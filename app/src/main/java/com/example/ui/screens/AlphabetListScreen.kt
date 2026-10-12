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
import com.example.data.AlphabetItem
import com.example.data.AlphabetRepository
import com.example.ui.components.KidTopBar
import com.example.ui.theme.PastelSky
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText

@Composable
fun AlphabetListScreen(
    stars: Int,
    learnedLetters: Set<String>,
    onSelectLetter: (Char) -> Unit,
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
            title = "Pilih Huruf A–Z",
            stars = stars,
            onBackClick = onBack,
            onStarClick = onStarClick
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            items(AlphabetRepository.items, key = { it.letter }) { item ->
                AlphabetCardItem(
                    item = item,
                    isLearned = learnedLetters.contains(item.letter.toString()),
                    onClick = { onSelectLetter(item.letter) }
                )
            }
        }
    }
}

@Composable
private fun AlphabetCardItem(
    item: AlphabetItem,
    isLearned: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        color = item.containerColor,
        shadowElevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .testTag("alphabet_card_${item.letter}")
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Learned badge (bintang / centang di kanan atas)
            if (isLearned) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Sudah Dipelajari",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = item.uppercase,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = item.primaryColor
                    )
                    Text(
                        text = item.lowercase,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = item.primaryColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(start = 3.dp, bottom = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "${item.emoji} ${item.word}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftDarkText,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}
