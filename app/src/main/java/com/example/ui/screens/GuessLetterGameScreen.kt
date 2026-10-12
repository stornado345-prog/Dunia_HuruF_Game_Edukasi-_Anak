package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ConfettiCelebrationOverlay
import com.example.ui.components.KidButton
import com.example.ui.components.KidTopBar
import com.example.ui.theme.BrightGold
import com.example.ui.theme.CoralRed
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText
import com.example.ui.viewmodel.GuessQuizState
import com.example.ui.viewmodel.QuestionType

@Composable
fun GuessLetterGameScreen(
    quizState: GuessQuizState,
    stars: Int,
    onAnswer: (Char) -> Unit,
    onRetry: () -> Unit,
    onNextQuestion: () -> Unit,
    onRestartQuiz: () -> Unit,
    onBack: () -> Unit,
    onStarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBackdrop)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            KidTopBar(
                title = "Tebak Huruf",
                stars = stars,
                onBackClick = onBack,
                onStarClick = onStarClick
            )

            if (quizState.isFinished) {
                // Tampilan Akhir Kuis (Perayaan Skor & Bintang)
                FinishedQuizView(
                    starsEarned = quizState.scoreThisRound,
                    onPlayAgain = onRestartQuiz,
                    onBackToMenu = onBack
                )
            } else {
                val currentQ = quizState.currentQuestion
                if (currentQ != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Indikator Nomor Soal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Soal ${quizState.currentQuestionIndex + 1} dari ${quizState.totalQuestions}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF546E7A)
                            )
                            Text(
                                text = "+${quizState.scoreThisRound} ⭐",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = BrightGold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Kartu Pertanyaan
                        Surface(
                            shape = RoundedCornerShape(26.dp),
                            color = currentQ.targetItem.containerColor,
                            shadowElevation = 4.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (currentQ.questionType == QuestionType.IMAGE_TO_LETTER) {
                                    Text(
                                        text = currentQ.targetItem.emoji,
                                        fontSize = 76.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = currentQ.targetItem.word.uppercase(),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black,
                                        color = currentQ.targetItem.primaryColor
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Huruf awal benda ini adalah...",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftDarkText
                                    )
                                } else {
                                    Text(
                                        text = "${currentQ.targetItem.uppercase} ${currentQ.targetItem.lowercase}",
                                        fontSize = 58.sp,
                                        fontWeight = FontWeight.Black,
                                        color = currentQ.targetItem.primaryColor
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Manakah gambar untuk huruf di atas?",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftDarkText,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Opsi Jawaban (Grid 2 x 2)
                        val optionColors = listOf(PastelBlue, PastelOrange, PastelGreen, PastelPink)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (rowIndex in 0..1) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    for (colIndex in 0..1) {
                                        val optIndex = rowIndex * 2 + colIndex
                                        if (optIndex < currentQ.options.size) {
                                            val option = currentQ.options[optIndex]
                                            val color = optionColors[optIndex % optionColors.size]
                                            val isSelected = quizState.selectedAnswerLetter == option.letter
                                            val isCorrectChoice = option.letter == currentQ.targetItem.letter

                                            val buttonBg = when {
                                                !quizState.isAnswered -> color
                                                isCorrectChoice -> Color(0xFF4CAF50)
                                                isSelected && !isCorrectChoice -> CoralRed
                                                else -> Color(0xFFCFD8DC)
                                            }

                                            Surface(
                                                onClick = {
                                                    if (!quizState.isAnswered) {
                                                        onAnswer(option.letter)
                                                    }
                                                },
                                                shape = RoundedCornerShape(22.dp),
                                                color = buttonBg,
                                                shadowElevation = 4.dp,
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(84.dp)
                                                    .testTag("quiz_option_${option.letter}")
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    if (currentQ.questionType == QuestionType.IMAGE_TO_LETTER) {
                                                        Text(
                                                            text = option.uppercase,
                                                            fontSize = 38.sp,
                                                            fontWeight = FontWeight.Black,
                                                            color = Color.White
                                                        )
                                                    } else {
                                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                            Text(
                                                                text = option.emoji,
                                                                fontSize = 32.sp
                                                            )
                                                            Text(
                                                                text = option.word,
                                                                fontSize = 13.sp,
                                                                fontWeight = FontWeight.Bold,
                                                                color = Color.White,
                                                                maxLines = 1
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Umpan Balik Positif & Tombol Lanjut
                        if (quizState.isAnswered) {
                            if (quizState.isCorrect) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = quizState.praiseMessage,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF2E7D32)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    KidButton(
                                        text = "Lanjut Soal Berikutnya ➡️",
                                        onClick = onNextQuestion,
                                        backgroundColor = PastelGreen,
                                        fontSize = 18,
                                        modifier = Modifier.fillMaxWidth(),
                                        testTag = "quiz_next_button"
                                    )
                                }
                            } else {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = quizState.praiseMessage,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CoralRed
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    KidButton(
                                        text = "Coba Lagi Ya! 🔄",
                                        onClick = onRetry,
                                        backgroundColor = PastelOrange,
                                        fontSize = 18,
                                        modifier = Modifier.fillMaxWidth(),
                                        testTag = "quiz_retry_button"
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }

        // Confetti animasi jika jawaban benar
        if (quizState.isAnswered && quizState.isCorrect) {
            ConfettiCelebrationOverlay()
        }
    }
}

@Composable
private fun FinishedQuizView(
    starsEarned: Int,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉",
            fontSize = 72.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Hore! Kuis Selesai!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = SoftDarkText
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Kamu berhasil mengumpulkan:",
            fontSize = 16.sp,
            color = Color(0xFF546E7A)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFFFF8E1),
            shadowElevation = 3.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(text = "⭐", fontSize = 28.sp)
                Text(
                    text = " +$starsEarned Bintang",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BrightGold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        KidButton(
            text = "Main Lagi 🎮",
            onClick = onPlayAgain,
            backgroundColor = PastelGreen,
            fontSize = 19,
            modifier = Modifier.fillMaxWidth(0.85f),
            testTag = "quiz_finish_play_again"
        )

        Spacer(modifier = Modifier.height(14.dp))

        KidButton(
            text = "Kembali ke Menu 🏠",
            onClick = onBackToMenu,
            backgroundColor = PastelBlue,
            fontSize = 18,
            modifier = Modifier.fillMaxWidth(0.85f),
            testTag = "quiz_finish_back_menu"
        )
    }
}
