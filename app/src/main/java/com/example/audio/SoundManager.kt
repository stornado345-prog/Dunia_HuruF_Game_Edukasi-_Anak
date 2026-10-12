package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.example.data.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.sin

class SoundManager(
    private val context: Context,
    private val preferencesManager: PreferencesManager
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false
    private val audioScope = CoroutineScope(Dispatchers.Default)
    private var bgmJob: Job? = null

    init {
        tts = TextToSpeech(context.applicationContext, this)
        // Start gentle ambient melodic notes if music is enabled
        audioScope.launch {
            preferencesManager.musicEnabledFlow.collect { enabled ->
                if (enabled) {
                    startGentleBgm()
                } else {
                    stopGentleBgm()
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale("id", "ID"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback ke Bahasa Indonesia umum atau default
                val fallbackResult = tts?.setLanguage(Locale("id"))
                if (fallbackResult == TextToSpeech.LANG_MISSING_DATA || fallbackResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.language = Locale.getDefault()
                }
            }
            tts?.setPitch(1.15f) // Nada sedikit ceria & ramah anak
            tts?.setSpeechRate(preferencesManager.getSpeechRate())
            isTtsReady = true

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {}
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {}
            })
        }
    }

    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
        if (!preferencesManager.isSfxEnabled()) return

        if (isTtsReady && tts != null) {
            tts?.setSpeechRate(preferencesManager.getSpeechRate())
            val params = Bundle()
            tts?.speak(text, queueMode, params, "kid_tts_${System.currentTimeMillis()}")
        } else {
            // Jika TTS belum siap, mainkan chime manis sebagai audio feedback
            playPop()
        }
    }

    fun speakLetterPhonics(letter: String, word: String) {
        speak("$letter! $word! Huruf $letter untuk $word.")
    }

    fun speakPraise(praise: String) {
        speak(praise)
    }

    // --- SFX Synthesizer (Zero missing mp3 files, always works offline) ---

    fun playPop() {
        if (!preferencesManager.isSfxEnabled()) return
        audioScope.launch {
            playToneSweep(startFreq = 400.0, endFreq = 800.0, durationMs = 80, volume = 0.5f)
        }
    }

    fun playSuccessChime() {
        if (!preferencesManager.isSfxEnabled()) return
        audioScope.launch {
            // Melodi Ceria C5 -> E5 -> G5 -> C6
            playTone(523.25, 90, 0.4f)
            delay(80)
            playTone(659.25, 90, 0.45f)
            delay(80)
            playTone(783.99, 100, 0.5f)
            delay(90)
            playTone(1046.50, 220, 0.6f)
        }
    }

    fun playFanfare() {
        if (!preferencesManager.isSfxEnabled()) return
        audioScope.launch {
            // Melodi perayaan bintang
            val notes = listOf(523.25, 659.25, 783.99, 1046.50, 1318.51)
            for (freq in notes) {
                playTone(freq, 110, 0.55f)
                delay(95)
            }
        }
    }

    fun playTryAgainSound() {
        if (!preferencesManager.isSfxEnabled()) return
        audioScope.launch {
            // Dua nada lembut ramah
            playTone(440.0, 120, 0.35f)
            delay(120)
            playTone(392.0, 180, 0.35f)
        }
    }

    private fun startGentleBgm() {
        if (bgmJob?.isActive == true) return
        bgmJob = audioScope.launch {
            // Melodi latar belakang anak-anak yang tenang, lembut, berulang santai
            val melody = listOf(
                Pair(523.25, 300L), // C
                Pair(659.25, 300L), // E
                Pair(783.99, 450L), // G
                Pair(659.25, 300L), // E
                Pair(587.33, 300L), // D
                Pair(698.46, 300L), // F
                Pair(783.99, 500L), // G
                Pair(523.25, 600L)  // C
            )
            while (isActive) {
                if (!preferencesManager.isMusicEnabled()) break
                for ((freq, dur) in melody) {
                    if (!isActive || !preferencesManager.isMusicEnabled()) break
                    playTone(freq, dur.toInt(), 0.12f) // Volume sangat lembut
                    delay(dur + 180L)
                }
                delay(3500L) // Jeda rileks sebelum melodi berikutnya
            }
        }
    }

    private fun stopGentleBgm() {
        bgmJob?.cancel()
        bgmJob = null
    }

    private fun playTone(freqHz: Double, durationMs: Int, volume: Float) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * durationMs / 1000.0).toInt()
            if (numSamples <= 0) return
            val buffer = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                // Envelope halus fade in & fade out agar tidak ada bunyi klik/pop
                val envelope = when {
                    i < 200 -> i / 200.0
                    i > numSamples - 400 -> (numSamples - i) / 400.0
                    else -> 1.0
                }
                val sample = (sin(2.0 * Math.PI * freqHz * t) * 32767.0 * volume * envelope).toInt()
                buffer[i] = sample.coerceIn(-32768, 32767).toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            // Lepaskan audioTrack setelah selesai memutar
            audioScope.launch {
                delay(durationMs + 80L)
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {
            // Ignore audio hardware transients
        }
    }

    private fun playToneSweep(startFreq: Double, endFreq: Double, durationMs: Int, volume: Float) {
        try {
            val sampleRate = 22050
            val numSamples = (sampleRate * durationMs / 1000.0).toInt()
            if (numSamples <= 0) return
            val buffer = ShortArray(numSamples)

            var phase = 0.0
            for (i in 0 until numSamples) {
                val progress = i.toDouble() / numSamples
                val currentFreq = startFreq + (endFreq - startFreq) * progress
                phase += 2.0 * Math.PI * currentFreq / sampleRate

                val envelope = when {
                    i < 100 -> i / 100.0
                    i > numSamples - 300 -> (numSamples - i) / 300.0
                    else -> 1.0
                }
                val sample = (sin(phase) * 32767.0 * volume * envelope).toInt()
                buffer[i] = sample.coerceIn(-32768, 32767).toShort()
            }

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            audioScope.launch {
                delay(durationMs + 60L)
                try {
                    audioTrack.stop()
                    audioTrack.release()
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
    }

    fun shutdown() {
        stopGentleBgm()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (_: Exception) {}
    }
}
