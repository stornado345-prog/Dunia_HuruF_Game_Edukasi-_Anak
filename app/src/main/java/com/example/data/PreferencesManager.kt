package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("dunia_huruf_prefs", Context.MODE_PRIVATE)

    private val _starsFlow = MutableStateFlow(getStars())
    val starsFlow: StateFlow<Int> = _starsFlow.asStateFlow()

    private val _learnedLettersFlow = MutableStateFlow(getLearnedLetters())
    val learnedLettersFlow: StateFlow<Set<String>> = _learnedLettersFlow.asStateFlow()

    private val _musicEnabledFlow = MutableStateFlow(isMusicEnabled())
    val musicEnabledFlow: StateFlow<Boolean> = _musicEnabledFlow.asStateFlow()

    private val _sfxEnabledFlow = MutableStateFlow(isSfxEnabled())
    val sfxEnabledFlow: StateFlow<Boolean> = _sfxEnabledFlow.asStateFlow()

    private val _speechRateFlow = MutableStateFlow(getSpeechRate())
    val speechRateFlow: StateFlow<Float> = _speechRateFlow.asStateFlow()

    fun getStars(): Int = prefs.getInt(KEY_STARS, 0)

    fun addStars(amount: Int) {
        val current = getStars()
        val newAmount = (current + amount).coerceAtLeast(0)
        prefs.edit().putInt(KEY_STARS, newAmount).apply()
        _starsFlow.value = newAmount
    }

    fun getLearnedLetters(): Set<String> {
        return prefs.getStringSet(KEY_LEARNED, emptySet()) ?: emptySet()
    }

    fun markLetterLearned(letter: String) {
        val current = getLearnedLetters().toMutableSet()
        if (current.add(letter)) {
            prefs.edit().putStringSet(KEY_LEARNED, current).apply()
            _learnedLettersFlow.value = current
            addStars(5) // Bonus 5 bintang saat menyelesaikan satu huruf baru!
        }
    }

    fun isMusicEnabled(): Boolean = prefs.getBoolean(KEY_MUSIC, true)

    fun setMusicEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_MUSIC, enabled).apply()
        _musicEnabledFlow.value = enabled
    }

    fun isSfxEnabled(): Boolean = prefs.getBoolean(KEY_SFX, true)

    fun setSfxEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SFX, enabled).apply()
        _sfxEnabledFlow.value = enabled
    }

    fun getSpeechRate(): Float = prefs.getFloat(KEY_SPEECH_RATE, 0.9f)

    fun setSpeechRate(rate: Float) {
        prefs.edit().putFloat(KEY_SPEECH_RATE, rate).apply()
        _speechRateFlow.value = rate
    }

    fun resetProgress() {
        prefs.edit().clear().apply()
        _starsFlow.value = 0
        _learnedLettersFlow.value = emptySet()
        _musicEnabledFlow.value = true
        _sfxEnabledFlow.value = true
        _speechRateFlow.value = 0.9f
    }

    companion object {
        private const val KEY_STARS = "key_stars"
        private const val KEY_LEARNED = "key_learned_letters"
        private const val KEY_MUSIC = "key_music"
        private const val KEY_SFX = "key_sfx"
        private const val KEY_SPEECH_RATE = "key_speech_rate"
    }
}
