package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPurple
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText
import com.example.ui.viewmodel.MatchQuizState

@Composable
fun MatchPairGameScreen(
    matchState: MatchQuizState,
    stars: Int,
    onSelectLetter: (Char) -> Unit,
    onSelectWord: (String) -> Unit,
    onNextRound: () -> Unit,
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
                title = "Cocokkan Kartu",
                stars = stars,
                onBackClick = onBack,
                onStarClick = onStarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Info Ronde & Petunjuk
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ronde ${matchState.roundIndex + 1} 🎯",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelBlue
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = matchState.feedbackMessage,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SoftDarkText,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Arena Pencocokan: Kolom Kiri Huruf, Kolom Kanan Objek
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Kolom Huruf
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "🔤 Huruf",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftDarkText,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        matchState.shuffledLetters.forEach { letter ->
                            val pair = matchState.pairs.find { it.letter == letter }
                            val isMatched = pair?.isMatched == true
                            val isSelected = matchState.selectedLetter == letter

                            LetterMatchCard(
                                letter = letter,
                                isMatched = isMatched,
                                isSelected = isSelected,
                                onClick = {
                                    if (!isMatched) onSelectLetter(letter)
                                }
                            )
                        }
                    }

                    // Kolom Benda
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "🎨 Benda",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftDarkText,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        matchState.shuffledWords.forEach { word ->
                            val pair = matchState.pairs.find { it.word == word }
                            val isMatched = pair?.isMatched == true
                            val isSelected = matchState.selectedWord == word
                            val emoji = pair?.emoji ?: "⭐"

                            WordMatchCard(
                                word = word,
                                emoji = emoji,
                                isMatched = isMatched,
                                isSelected = isSelected,
                                onClick = {
                                    if (!isMatched) onSelectWord(word)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Jika ronde selesai, tampilkan tombol Ronde Berikutnya
                if (matchState.isRoundComplete) {
                    KidButton(
                        text = "Ronde Berikutnya! 🚀",
                        onClick = onNextRound,
                        backgroundColor = PastelGreen,
                        fontSize = 19,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "match_next_round_button"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        if (matchState.isRoundComplete) {
            ConfettiCelebrationOverlay()
        }
    }
}

@Composable
private fun LetterMatchCard(
    letter: Char,
    isMatched: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isMatched -> Color(0xFFC8E6C9)
        isSelected -> Color(0xFFFFF9C4)
        else -> Color.White
    }
    val borderColor = when {
        isMatched -> Color(0xFF4CAF50)
        isSelected -> PastelOrange
        else -> Color(0xFFE0E0E0)
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = bgColor,
        shadowElevation = if (isSelected) 6.dp else 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .border(width = if (isSelected || isMatched) 3.dp else 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .testTag("match_letter_$letter")
    ) {
        Box(contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$letter",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isMatched) Color(0xFF2E7D32) else PastelBlue
                )
                if (isMatched) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Cocok",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WordMatchCard(
    word: String,
    emoji: String,
    isMatched: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = when {
        isMatched -> Color(0xFFC8E6C9)
        isSelected -> Color(0xFFFFF9C4)
        else -> Color.White
    }
    val borderColor = when {
        isMatched -> Color(0xFF4CAF50)
        isSelected -> PastelOrange
        else -> Color(0xFFE0E0E0)
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = bgColor,
        shadowElevation = if (isSelected) 6.dp else 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .border(width = if (isSelected || isMatched) 3.dp else 1.dp, color = borderColor, shape = RoundedCornerShape(20.dp))
            .testTag("match_word_$word")
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = emoji,
                    fontSize = 28.sp
                )
                Text(
                    text = word,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isMatched) Color(0xFF2E7D32) else SoftDarkText,
                    maxLines = 1
                )
            }
        }
    }
}
