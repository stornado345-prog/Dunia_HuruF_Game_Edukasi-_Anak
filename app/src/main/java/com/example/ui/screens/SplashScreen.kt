package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KidButton
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelSky
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftDarkText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onStartGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Otomatis pindah ke Home setelah 2.2 detik atau saat anak menekan tombol Mulai
    LaunchedEffect(Unit) {
        delay(2200)
        onStartGame()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "letter_bounce")
    val bounceA by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounceA"
    )
    val bounceB by infiniteTransition.animateFloat(
        initialValue = 12f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounceB"
    )
    val bounceC by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 14f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounceC"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PastelSky,
                        Color(0xFFFFF9C4),
                        Color(0xFFE8F5E9)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Blok Huruf Beranimasi A B C
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AlphabetSplashBlock(letter = "A", color = PastelPink, offset = bounceA)
                AlphabetSplashBlock(letter = "B", color = PastelYellow, offset = bounceB)
                AlphabetSplashBlock(letter = "C", color = PastelGreen, offset = bounceC)
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Judul Aplikasi
            Text(
                text = "Dunia Huruf",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = SoftDarkText,
                textAlign = TextAlign.Center
            )

            Text(
                text = "✨ Belajar ABC Ceria ✨",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PastelBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mengenal alfabet menyenangkan untuk anak pintar!",
                fontSize = 15.sp,
                color = Color(0xFF546E7A),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Tombol Mulai Langsung
            KidButton(
                text = "Ayo Mulai! 🎈",
                onClick = onStartGame,
                backgroundColor = Color(0xFFFF9800),
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                testTag = "splash_start_button"
            )
        }
    }
}

@Composable
private fun AlphabetSplashBlock(
    letter: String,
    color: Color,
    offset: Float
) {
    Surface(
        modifier = Modifier
            .size(72.dp)
            .offset(y = offset.dp)
            .shadow(6.dp, RoundedCornerShape(20.dp), spotColor = color),
        shape = RoundedCornerShape(20.dp),
        color = color
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = letter,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}
