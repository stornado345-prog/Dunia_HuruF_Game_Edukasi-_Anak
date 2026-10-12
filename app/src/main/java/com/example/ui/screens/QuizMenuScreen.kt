package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KidTopBar
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPink
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText

@Composable
fun QuizMenuScreen(
    stars: Int,
    onStartGuessGame: () -> Unit,
    onStartMatchGame: () -> Unit,
    onBack: () -> Unit,
    onStarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBackdrop)
    ) {
        KidTopBar(
            title = "Bermain Kuis",
            stars = stars,
            onBackClick = onBack,
            onStarClick = onStarClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pilih Permainan Seru! 🎈",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = SoftDarkText,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Game 1: Tebak Huruf
            QuizSelectionCard(
                title = "Tebak Huruf & Gambar 🧩",
                subtitle = "Tebak huruf awal atau gambar yang cocok. Dapatkan +10 ⭐ per soal!",
                badgeEmoji = "🎯",
                cardColor = Color(0xFFFFF3E0),
                accentColor = PastelOrange,
                onClick = onStartGuessGame,
                testTag = "quiz_menu_guess_button"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Game 2: Mencocokkan Pasangan
            QuizSelectionCard(
                title = "Mencocokkan Huruf 🔗",
                subtitle = "Pasangkan huruf dengan bendanya yang tepat. Dapatkan +15 ⭐!",
                badgeEmoji = "✨",
                cardColor = Color(0xFFE8F5E9),
                accentColor = PastelGreen,
                onClick = onStartMatchGame,
                testTag = "quiz_menu_match_button"
            )
        }
    }
}

@Composable
private fun QuizSelectionCard(
    title: String,
    subtitle: String,
    badgeEmoji: String,
    cardColor: Color,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(26.dp),
        color = cardColor,
        shadowElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = accentColor,
                modifier = Modifier.size(64.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = badgeEmoji,
                        fontSize = 32.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftDarkText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color(0xFF546E7A),
                    lineHeight = 18.sp
                )
            }
        }
    }
}
