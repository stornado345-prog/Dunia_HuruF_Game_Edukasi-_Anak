package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AlphabetDetailScreen
import com.example.ui.screens.AlphabetListScreen
import com.example.ui.screens.GuessLetterGameScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MatchPairGameScreen
import com.example.ui.screens.QuizMenuScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StarCollectionScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.GameViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DuniaHurufApp()
                }
            }
        }
    }
}

@Composable
fun DuniaHurufApp(
    viewModel: GameViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val stars by viewModel.stars.collectAsStateWithLifecycle()
    val learnedLetters by viewModel.learnedLetters.collectAsStateWithLifecycle()
    val musicEnabled by viewModel.musicEnabled.collectAsStateWithLifecycle()
    val sfxEnabled by viewModel.sfxEnabled.collectAsStateWithLifecycle()
    val speechRate by viewModel.speechRate.collectAsStateWithLifecycle()
    val guessQuizState by viewModel.guessQuizState.collectAsStateWithLifecycle()
    val matchQuizState by viewModel.matchQuizState.collectAsStateWithLifecycle()

    // Tangani tombol Back perangkat agar selalu kembali ke layar sebelumnya atau ke Home
    if (currentScreen != Screen.HOME && currentScreen != Screen.SPLASH) {
        BackHandler {
            viewModel.navigateBack()
        }
    }

    when (currentScreen) {
        Screen.SPLASH -> {
            SplashScreen(
                onStartGame = { viewModel.navigateTo(Screen.HOME) }
            )
        }

        Screen.HOME -> {
            HomeScreen(
                stars = stars,
                learnedLettersCount = learnedLetters.size,
                onStartLearning = { viewModel.navigateTo(Screen.ALPHABET_LIST) },
                onStartQuiz = { viewModel.navigateTo(Screen.QUIZ_MENU) },
                onViewRewards = { viewModel.navigateTo(Screen.STARS_COLLECTION) },
                onOpenSettings = { viewModel.navigateTo(Screen.SETTINGS) }
            )
        }

        Screen.ALPHABET_LIST -> {
            AlphabetListScreen(
                stars = stars,
                learnedLetters = learnedLetters,
                onSelectLetter = { letter -> viewModel.selectLetter(letter) },
                onBack = { viewModel.navigateBack() },
                onStarClick = { viewModel.navigateTo(Screen.STARS_COLLECTION) }
            )
        }

        Screen.ALPHABET_DETAIL -> {
            val currentItem = viewModel.getCurrentItem()
            AlphabetDetailScreen(
                item = currentItem,
                stars = stars,
                onSpeakAll = { viewModel.speakCurrentItem() },
                onSpeakWord = { viewModel.speakWordOnly() },
                onSpeakSentence = { viewModel.speakSentence() },
                onNext = { viewModel.nextLetter() },
                onPrev = { viewModel.prevLetter() },
                onBack = { viewModel.navigateBack() },
                onStarClick = { viewModel.navigateTo(Screen.STARS_COLLECTION) }
            )
        }

        Screen.QUIZ_MENU -> {
            QuizMenuScreen(
                stars = stars,
                onStartGuessGame = { viewModel.startGuessGame() },
                onStartMatchGame = { viewModel.startMatchGame() },
                onBack = { viewModel.navigateBack() },
                onStarClick = { viewModel.navigateTo(Screen.STARS_COLLECTION) }
            )
        }

        Screen.GUESS_LETTER_GAME -> {
            GuessLetterGameScreen(
                quizState = guessQuizState,
                stars = stars,
                onAnswer = { letter -> viewModel.answerGuessQuestion(letter) },
                onRetry = { viewModel.retryCurrentGuessQuestion() },
                onNextQuestion = { viewModel.nextGuessQuestion() },
                onRestartQuiz = { viewModel.startGuessGame() },
                onBack = { viewModel.navigateBack() },
                onStarClick = { viewModel.navigateTo(Screen.STARS_COLLECTION) }
            )
        }

        Screen.MATCH_PAIR_GAME -> {
            MatchPairGameScreen(
                matchState = matchQuizState,
                stars = stars,
                onSelectLetter = { letter -> viewModel.selectMatchLetter(letter) },
                onSelectWord = { word -> viewModel.selectMatchWord(word) },
                onNextRound = { viewModel.nextMatchRound() },
                onBack = { viewModel.navigateBack() },
                onStarClick = { viewModel.navigateTo(Screen.STARS_COLLECTION) }
            )
        }

        Screen.STARS_COLLECTION -> {
            StarCollectionScreen(
                stars = stars,
                onBack = { viewModel.navigateBack() }
            )
        }

        Screen.SETTINGS -> {
            SettingsScreen(
                stars = stars,
                musicEnabled = musicEnabled,
                sfxEnabled = sfxEnabled,
                speechRate = speechRate,
                onToggleMusic = { viewModel.toggleMusic() },
                onToggleSfx = { viewModel.toggleSfx() },
                onChangeSpeechRate = { rate -> viewModel.setSpeechRate(rate) },
                onTestSound = {
                    viewModel.soundManager.speakLetterPhonics("A", "Apel")
                },
                onResetProgress = { viewModel.resetProgress() },
                onBack = { viewModel.navigateBack() }
            )
        }
    }
}
