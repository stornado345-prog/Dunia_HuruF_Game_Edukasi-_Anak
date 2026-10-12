package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BrightGold
import com.example.ui.theme.BrightStar
import com.example.ui.theme.SoftDarkText
import kotlin.random.Random

@Composable
fun KidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: String? = null,
    backgroundColor: Color = Color(0xFFFFB74D),
    contentColor: Color = Color.White,
    fontSize: Int = 20,
    height: Dp = 64.dp,
    testTag: String = "kid_button"
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .testTag(testTag)
            .height(height)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = backgroundColor.copy(alpha = 0.5f)
            ),
        shape = RoundedCornerShape(28.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Text(
                    text = icon,
                    fontSize = (fontSize + 6).sp,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
            Text(
                text = text,
                color = contentColor,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun KidStarBadge(
    stars: Int,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "star_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_scale"
    )

    Surface(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFFFFF8E1),
        shadowElevation = 3.dp,
        modifier = modifier.testTag("star_badge")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text = "⭐",
                fontSize = 20.sp,
                modifier = Modifier
                    .scale(scale)
                    .padding(end = 6.dp)
            )
            Text(
                text = "$stars",
                color = BrightGold,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun KidTopBar(
    title: String,
    stars: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onStarClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Tombol Kembali Besar
        Surface(
            onClick = onBackClick,
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp,
            modifier = Modifier
                .size(48.dp)
                .testTag("back_button")
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = SoftDarkText,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        // Judul Ceria
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = SoftDarkText,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Indikator Bintang
        KidStarBadge(
            stars = stars,
            onClick = onStarClick
        )
    }
}

@Composable
fun ConfettiCelebrationOverlay(
    modifier: Modifier = Modifier
) {
    val particles = remember {
        List(40) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 16f + 8f,
                color = listOf(
                    Color(0xFFFF5252),
                    Color(0xFFFFD740),
                    Color(0xFF69F0AE),
                    Color(0xFF40C4FF),
                    Color(0xFFFF4081),
                    Color(0xFFE040FB)
                ).random(),
                speed = Random.nextFloat() * 0.015f + 0.005f
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "confetti_anim")
    val animProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confetti_progress"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val curY = ((p.y + animProgress * p.speed * 80f) % 1f) * size.height
            val curX = p.x * size.width
            drawCircle(
                color = p.color,
                radius = p.size,
                center = Offset(curX, curY)
            )
        }
    }
}

private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val size: Float,
    val color: Color,
    val speed: Float
)
