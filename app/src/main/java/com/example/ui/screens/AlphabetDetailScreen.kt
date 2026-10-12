package com.example.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlphabetItem
import com.example.ui.components.KidButton
import com.example.ui.components.KidTopBar
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText

@Composable
fun AlphabetDetailScreen(
    item: AlphabetItem,
    stars: Int,
    onSpeakAll: () -> Unit,
    onSpeakWord: () -> Unit,
    onSpeakSentence: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    onBack: () -> Unit,
    onStarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isTapped by remember(item.letter) { mutableStateOf(false) }
    val scaleAnim by animateFloatAsState(
        targetValue = if (isTapped) 1.25f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "emoji_bounce"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBackdrop)
    ) {
        KidTopBar(
            title = "Huruf ${item.uppercase}",
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
            // Kartu Utama Huruf & Objek
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = item.containerColor,
                shadowElevation = 5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("detail_card")
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Huruf Besar & Kecil Raksasa
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.uppercase,
                            fontSize = 84.sp,
                            fontWeight = FontWeight.Black,
                            color = item.primaryColor
                        )
                        Text(
                            text = item.lowercase,
                            fontSize = 62.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = item.primaryColor.copy(alpha = 0.85f),
                            modifier = Modifier.padding(start = 12.dp, bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Ilustrasi Kartun Emojicon (Bisa di-tap untuk suara dan melompat)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(130.dp)
                            .scale(scaleAnim)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                isTapped = !isTapped
                                onSpeakWord()
                            }
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.85f),
                            shadowElevation = 4.dp,
                            modifier = Modifier.size(120.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = item.emoji,
                                    fontSize = 72.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nama Objek dalam Bahasa Indonesia
                    Text(
                        text = item.word.uppercase(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = item.primaryColor,
                        letterSpacing = 2.sp
                    )

                    // Kategori
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = item.category,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SoftDarkText,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Kalimat Contoh Bahasa Indonesia
                    Text(
                        text = item.sentence,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF37474F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Tombol Audio Pelafalan (Besar, Jelas, Nyaman Di-tap Anak)
            KidButton(
                text = "Dengarkan Pelafalan 🔊",
                onClick = onSpeakAll,
                backgroundColor = item.primaryColor,
                fontSize = 18,
                height = 62.dp,
                modifier = Modifier.fillMaxWidth(),
                testTag = "detail_speak_all_button"
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                KidButton(
                    text = "Sebut Kata 🗣️",
                    onClick = onSpeakWord,
                    backgroundColor = PastelOrange,
                    fontSize = 15,
                    height = 54.dp,
                    modifier = Modifier.weight(1f),
                    testTag = "detail_speak_word_button"
                )
                KidButton(
                    text = "Baca Kalimat 📖",
                    onClick = onSpeakSentence,
                    backgroundColor = PastelBlue,
                    fontSize = 15,
                    height = 54.dp,
                    modifier = Modifier.weight(1f),
                    testTag = "detail_speak_sentence_button"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Navigasi Huruf Sebelumnya (<) dan Berikutnya (>)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onPrev,
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White,
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .height(54.dp)
                        .weight(1f)
                        .testTag("detail_prev_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Huruf Sebelumnya",
                            tint = SoftDarkText
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sebelum",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = SoftDarkText
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                Surface(
                    onClick = onNext,
                    shape = RoundedCornerShape(22.dp),
                    color = PastelYellow,
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .height(54.dp)
                        .weight(1f)
                        .testTag("detail_next_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    ) {
                        Text(
                            text = "Berikutnya",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = SoftDarkText
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Huruf Berikutnya",
                            tint = SoftDarkText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
