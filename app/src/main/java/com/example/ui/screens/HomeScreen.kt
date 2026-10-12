package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edukasi_Game_Anak.R
import com.example.data.RewardRepository
import com.example.ui.components.KidButton
import com.example.ui.components.KidStarBadge
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelPurple
import com.example.ui.theme.PastelSky
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftDarkText

@Composable
fun HomeScreen(
    stars: Int,
    learnedLettersCount: Int,
    onStartLearning: () -> Unit,
    onStartQuiz: () -> Unit,
    onViewRewards: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val levelInfo = RewardRepository.getLevel(stars)
    val progressFraction = (learnedLettersCount.toFloat() / 26f).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PastelSky,
                        Color(0xFFFFFDE7),
                        Color(0xFFF1F8E9)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Judul App & Bintang Anak
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Dunia Huruf",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SoftDarkText
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "${levelInfo.badgeEmoji} ${levelInfo.levelName}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF455A64)
                        )
                    }
                }

                KidStarBadge(
                    stars = stars,
                    onClick = onViewRewards
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hero Banner Kartun Anak
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .shadow(6.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.kids_hero_banner),
                        contentDescription = "Banner Dunia Huruf",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Soft gradient overlay at bottom
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0x66000000)),
                                    startY = 200f
                                )
                            )
                    )
                    Text(
                        text = "Belajar Huruf ABC Jadi Seru! 🎨",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Progres Belajar Huruf
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progres Belajar A-Z",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftDarkText
                        )
                        Text(
                            text = "$learnedLettersCount / 26 Huruf",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PastelBlue
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = PastelGreen,
                        trackColor = Color(0xFFEEEEEE),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tombol 1: Mulai Belajar (Besar dan Menonjol)
            KidButton(
                text = "Mulai Belajar A–Z 🚀",
                onClick = onStartLearning,
                backgroundColor = PastelBlue,
                fontSize = 20,
                height = 70.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "home_learn_button"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Tombol 2: Bermain Kuis
            KidButton(
                text = "Bermain Kuis 🎮",
                onClick = onStartQuiz,
                backgroundColor = PastelOrange,
                fontSize = 19,
                height = 64.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "home_quiz_button"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Tombol 3: Koleksi Bintang & Hadiah
            KidButton(
                text = "Koleksi Bintang ⭐",
                onClick = onViewRewards,
                backgroundColor = PastelPink,
                fontSize = 19,
                height = 64.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "home_stars_button"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Tombol 4: Pengaturan Suara & Aplikasi
            KidButton(
                text = "Pengaturan ⚙️",
                onClick = onOpenSettings,
                backgroundColor = Color(0xFF78909C),
                fontSize = 18,
                height = 58.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "home_settings_button"
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
