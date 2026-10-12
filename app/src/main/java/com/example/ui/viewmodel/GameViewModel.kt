package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.AlphabetItem
import com.example.data.AlphabetRepository
import com.example.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    SPLASH,
    HOME,
    ALPHABET_LIST,
    ALPHABET_DETAIL,
    QUIZ_MENU,
    GUESS_LETTER_GAME,
    MATCH_PAIR_GAME,
    STARS_COLLECTION,
    SETTINGS
}

data class GuessQuestion(
    val targetItem: AlphabetItem,
    val options: List<AlphabetItem>,
    val questionType: QuestionType = QuestionType.IMAGE_TO_LETTER
)

enum class QuestionType {
    IMAGE_TO_LETTER, // "Manakah huruf awal dari Apel?"
    LETTER_TO_IMAGE  // "Manakah gambar untuk huruf A?"
}

data class GuessQuizState(
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 5,
    val currentQuestion: GuessQuestion? = null,
    val isAnswered: Boolean = false,
    val selectedAnswerLetter: Char? = null,
    val isCorrect: Boolean = false,
    val praiseMessage: String = "",
    val scoreThisRound: Int = 0,
    val isFinished: Boolean = false
)

data class MatchPair(
    val letter: Char,
    val word: String,
    val emoji: String,
    val isMatched: Boolean = false
)

data class MatchQuizState(
    val roundIndex: Int = 0,
    val pairs: List<MatchPair> = emptyList(),
    val shuffledLetters: List<Char> = emptyList(),
    val shuffledWords: List<String> = emptyList(),
    val selectedLetter: Char? = null,
    val selectedWord: String? = null,
    val matchedCount: Int = 0,
    val isRoundComplete: Boolean = false,
    val feedbackMessage: String = ""
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application.applicationContext)
    val soundManager = SoundManager(application.applicationContext, preferencesManager)

    private val screenStack = mutableListOf<Screen>()

    private val _currentScreen = MutableStateFlow(Screen.SPLASH)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _selectedLetter = MutableStateFlow('A')
    val selectedLetter: StateFlow<Char> = _selectedLetter.asStateFlow()

    val stars: StateFlow<Int> = preferencesManager.starsFlow
    val learnedLetters: StateFlow<Set<String>> = preferencesManager.learnedLettersFlow
    val musicEnabled: StateFlow<Boolean> = preferencesManager.musicEnabledFlow
    val sfxEnabled: StateFlow<Boolean> = preferencesManager.sfxEnabledFlow
    val speechRate: StateFlow<Float> = preferencesManager.speechRateFlow

    private val _guessQuizState = MutableStateFlow(GuessQuizState())
    val guessQuizState: StateFlow<GuessQuizState> = _guessQuizState.asStateFlow()

    private val _matchQuizState = MutableStateFlow(MatchQuizState())
    val matchQuizState: StateFlow<MatchQuizState> = _matchQuizState.asStateFlow()

    private val praiseMessages = listOf(
        "Hebat Sekali! 🎉",
        "Pintar Banget! ⭐",
        "Luar Biasa! 🌟",
        "Wah, Kamu Juara! 🏆",
        "Keren Banget! 🚀"
    )

    fun navigateTo(screen: Screen) {
        soundManager.playPop()
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        soundManager.playPop()
        return if (screenStack.isNotEmpty()) {
            val previous = screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = previous
            true
        } else if (_currentScreen.value != Screen.HOME) {
            _currentScreen.value = Screen.HOME
            true
        } else {
            false
        }
    }

    // --- Alphabet Feature ---

    fun selectLetter(letter: Char) {
        soundManager.playPop()
        _selectedLetter.value = letter
        navigateTo(Screen.ALPHABET_DETAIL)
        speakCurrentItem()
    }

    fun nextLetter() {
        val currentIndex = AlphabetRepository.items.indexOfFirst { it.letter == _selectedLetter.value }
        if (currentIndex < AlphabetRepository.items.size - 1) {
            soundManager.playPop()
            _selectedLetter.value = AlphabetRepository.items[currentIndex + 1].letter
            speakCurrentItem()
        }
    }

    fun prevLetter() {
        val currentIndex = AlphabetRepository.items.indexOfFirst { it.letter == _selectedLetter.value }
        if (currentIndex > 0) {
            soundManager.playPop()
            _selectedLetter.value = AlphabetRepository.items[currentIndex - 1].letter
            speakCurrentItem()
        }
    }

    fun getCurrentItem(): AlphabetItem {
        return AlphabetRepository.getItem(_selectedLetter.value)
    }

    fun speakCurrentItem() {
        val item = getCurrentItem()
        preferencesManager.markLetterLearned(item.letter.toString())
        soundManager.speakLetterPhonics(item.letter.toString(), item.word)
    }

    fun speakWordOnly() {
        val item = getCurrentItem()
        soundManager.speak(item.word)
    }

    fun speakSentence() {
        val item = getCurrentItem()
        soundManager.speak(item.sentence)
    }

    // --- Guess Quiz Mini-Game ---

    fun startGuessGame() {
        generateGuessQuestion(0, 0)
        navigateTo(Screen.GUESS_LETTER_GAME)
    }

    private fun generateGuessQuestion(index: Int, currentScore: Int) {
        val allItems = AlphabetRepository.items.shuffled()
        val target = allItems.first()
        val distractors = allItems.filter { it.letter != target.letter }.take(3)
        val options = (distractors + target).shuffled()
        val questionType = if (index % 2 == 0) QuestionType.IMAGE_TO_LETTER else QuestionType.LETTER_TO_IMAGE

        _guessQuizState.value = GuessQuizState(
            currentQuestionIndex = index,
            totalQuestions = 5,
            currentQuestion = GuessQuestion(target, options, questionType),
            isAnswered = false,
            selectedAnswerLetter = null,
            isCorrect = false,
            praiseMessage = "",
            scoreThisRound = currentScore,
            isFinished = false
        )

        // Ucapkan pertanyaan ramah anak
        if (questionType == QuestionType.IMAGE_TO_LETTER) {
            soundManager.speak("Manakah huruf awal dari ${target.word}?")
        } else {
            soundManager.speak("Manakah gambar untuk huruf ${target.letter}?")
        }
    }

    fun answerGuessQuestion(selectedLetter: Char) {
        val state = _guessQuizState.value
        if (state.isAnswered) return
        val currentQ = state.currentQuestion ?: return

        val isCorrect = selectedLetter == currentQ.targetItem.letter
        val randomPraise = praiseMessages.random()

        if (isCorrect) {
            soundManager.playSuccessChime()
            soundManager.speakPraise(randomPraise)
            preferencesManager.addStars(10)
            _guessQuizState.value = state.copy(
                isAnswered = true,
                selectedAnswerLetter = selectedLetter,
                isCorrect = true,
                praiseMessage = randomPraise,
                scoreThisRound = state.scoreThisRound + 10
            )
        } else {
            soundManager.playTryAgainSound()
            soundManager.speak("Coba lagi ya, kamu pasti bisa!")
            _guessQuizState.value = state.copy(
                isAnswered = true,
                selectedAnswerLetter = selectedLetter,
                isCorrect = false,
                praiseMessage = "Yuk coba perhatikan lagi!"
            )
        }
    }

    fun retryCurrentGuessQuestion() {
        soundManager.playPop()
        val state = _guessQuizState.value
        _guessQuizState.value = state.copy(
            isAnswered = false,
            selectedAnswerLetter = null,
            isCorrect = false,
            praiseMessage = ""
        )
    }

    fun nextGuessQuestion() {
        soundManager.playPop()
        val state = _guessQuizState.value
        val nextIdx = state.currentQuestionIndex + 1
        if (nextIdx < state.totalQuestions) {
            generateGuessQuestion(nextIdx, state.scoreThisRound)
        } else {
            // Selesai 5 soal
            soundManager.playFanfare()
            _guessQuizState.value = state.copy(isFinished = true)
        }
    }

    // --- Match Pair Mini-Game ---

    fun startMatchGame() {
        generateMatchRound(0)
        navigateTo(Screen.MATCH_PAIR_GAME)
    }

    private fun generateMatchRound(roundIndex: Int) {
        val selectedItems = AlphabetRepository.items.shuffled().take(3)
        val pairs = selectedItems.map {
            MatchPair(letter = it.letter, word = it.word, emoji = it.emoji)
        }

        _matchQuizState.value = MatchQuizState(
            roundIndex = roundIndex,
            pairs = pairs,
            shuffledLetters = pairs.map { it.letter }.shuffled(),
            shuffledWords = pairs.map { it.word }.shuffled(),
            selectedLetter = null,
            selectedWord = null,
            matchedCount = 0,
            isRoundComplete = false,
            feedbackMessage = "Pasangkan huruf dengan gambarnya!"
        )

        soundManager.speak("Ayo pasangkan huruf dengan bendanya!")
    }

    fun selectMatchLetter(letter: Char) {
        soundManager.playPop()
        val state = _matchQuizState.value
        _matchQuizState.value = state.copy(selectedLetter = letter)
        checkMatchPair()
    }

    fun selectMatchWord(word: String) {
        soundManager.playPop()
        val state = _matchQuizState.value
        _matchQuizState.value = state.copy(selectedWord = word)
        checkMatchPair()
    }

    private fun checkMatchPair() {
        val state = _matchQuizState.value
        val letter = state.selectedLetter ?: return
        val word = state.selectedWord ?: return

        // Cari apakah cocok
        val matchedPair = state.pairs.find { it.letter == letter && it.word == word }
        if (matchedPair != null && !matchedPair.isMatched) {
            // Cocok!
            soundManager.playSuccessChime()
            soundManager.speak("Hebat! $letter untuk $word!")
            preferencesManager.addStars(15)

            val updatedPairs = state.pairs.map {
                if (it.letter == letter) it.copy(isMatched = true) else it
            }
            val newMatchedCount = state.matchedCount + 1
            val isRoundComplete = newMatchedCount >= state.pairs.size

            if (isRoundComplete) {
                soundManager.playFanfare()
            }

            _matchQuizState.value = state.copy(
                pairs = updatedPairs,
                selectedLetter = null,
                selectedWord = null,
                matchedCount = newMatchedCount,
                isRoundComplete = isRoundComplete,
                feedbackMessage = if (isRoundComplete) "Hore! Semua kartu berhasil dipasangkan!" else "Pintar! Cocok!"
            )
        } else {
            // Tidak cocok
            soundManager.playTryAgainSound()
            _matchQuizState.value = state.copy(
                selectedLetter = null,
                selectedWord = null,
                feedbackMessage = "Belum cocok, coba pasangkan lagi!"
            )
        }
    }

    fun nextMatchRound() {
        soundManager.playPop()
        val state = _matchQuizState.value
        generateMatchRound(state.roundIndex + 1)
    }

    // --- Settings ---

    fun toggleMusic() {
        soundManager.playPop()
        val current = musicEnabled.value
        preferencesManager.setMusicEnabled(!current)
    }

    fun toggleSfx() {
        soundManager.playPop()
        val current = sfxEnabled.value
        preferencesManager.setSfxEnabled(!current)
    }

    fun setSpeechRate(rate: Float) {
        preferencesManager.setSpeechRate(rate)
    }

    fun resetProgress() {
        soundManager.playPop()
        preferencesManager.resetProgress()
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.shutdown()
    }
}
