package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.RewardItem
import com.example.data.RewardRepository
import com.example.ui.components.KidTopBar
import com.example.ui.theme.BrightGold
import com.example.ui.theme.BrightStar
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText

@Composable
fun StarCollectionScreen(
    stars: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val levelInfo = RewardRepository.getLevel(stars)
    val progressToNext = (stars.toFloat() / levelInfo.nextLevelStars).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBackdrop)
    ) {
        KidTopBar(
            title = "Koleksi Hadiah",
            stars = stars,
            onBackClick = onBack
        )

        // Kartu Level & Total Bintang
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFFFF8E1),
            shadowElevation = 3.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "⭐", fontSize = 34.sp)
                    Text(
                        text = " $stars Bintang",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = BrightGold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${levelInfo.badgeEmoji} Level ${levelInfo.levelNumber}: ${levelInfo.levelName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftDarkText
                )

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { progressToNext },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = BrightGold,
                    trackColor = Color(0xFFFFECB3),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$stars / ${levelInfo.nextLevelStars} ⭐ menuju level berikutnya",
                    fontSize = 12.sp,
                    color = Color(0xFF757575)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Stiker & Piala Penghargaan 🏆",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = SoftDarkText,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        // Grid Hadiah & Stiker
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            items(RewardRepository.rewards, key = { it.id }) { reward ->
                RewardStickerCard(
                    reward = reward,
                    isUnlocked = stars >= reward.requiredStars
                )
            }
        }
    }
}

@Composable
private fun RewardStickerCard(
    reward: RewardItem,
    isUnlocked: Boolean
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = if (isUnlocked) Color.White else Color(0xFFECEFF1),
        shadowElevation = if (isUnlocked) 4.dp else 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .testTag("reward_card_${reward.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(56.dp)
            ) {
                if (isUnlocked) {
                    Text(
                        text = reward.emoji,
                        fontSize = 38.sp
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFCFD8DC),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🔒", fontSize = 22.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = reward.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isUnlocked) SoftDarkText else Color(0xFF78909C),
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (isUnlocked) {
                Text(
                    text = "Terbuka! ✨",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PastelGreen
                )
            } else {
                Text(
                    text = "Butuh ${reward.requiredStars} ⭐",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE65100)
                )
            }
        }
    }
}
