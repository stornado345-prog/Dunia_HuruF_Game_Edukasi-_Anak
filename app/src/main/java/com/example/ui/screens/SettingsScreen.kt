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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.KidButton
import com.example.ui.components.KidTopBar
import com.example.ui.theme.CoralRed
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.SoftBackdrop
import com.example.ui.theme.SoftDarkText

@Composable
fun SettingsScreen(
    stars: Int,
    musicEnabled: Boolean,
    sfxEnabled: Boolean,
    speechRate: Float,
    onToggleMusic: () -> Unit,
    onToggleSfx: () -> Unit,
    onChangeSpeechRate: (Float) -> Unit,
    onTestSound: () -> Unit,
    onResetProgress: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SoftBackdrop)
    ) {
        KidTopBar(
            title = "Pengaturan",
            stars = stars,
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pengaturan Audio
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Suara & Musik 🎵",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoftDarkText
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Toggle Musik BGM
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Musik Ceria Latar",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SoftDarkText
                            )
                            Text(
                                text = "Melodi lembut saat aplikasi dibuka",
                                fontSize = 12.sp,
                                color = Color(0xFF78909C)
                            )
                        }
                        Switch(
                            checked = musicEnabled,
                            onCheckedChange = { onToggleMusic() },
                            colors = SwitchDefaults.colors(checkedThumbColor = PastelGreen),
                            modifier = Modifier.testTag("switch_music")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Toggle SFX
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Efek Suara (SFX)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SoftDarkText
                            )
                            Text(
                                text = "Suara klik, chime bintang & tepukan",
                                fontSize = 12.sp,
                                color = Color(0xFF78909C)
                            )
                        }
                        Switch(
                            checked = sfxEnabled,
                            onCheckedChange = { onToggleSfx() },
                            colors = SwitchDefaults.colors(checkedThumbColor = PastelBlue),
                            modifier = Modifier.testTag("switch_sfx")
                        )
                    }
                }
            }

            // Pengaturan Kecepatan Bicara Pelafalan
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Kecepatan Pelafalan Alfabet 🗣️",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoftDarkText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sesuaikan kecepatan agar mudah disimak anak usia 4-6 tahun",
                        fontSize = 12.sp,
                        color = Color(0xFF78909C)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Perlahan (0.7x)", fontSize = 12.sp, color = Color(0xFF546E7A))
                        Text(
                            text = String.format("%.1fx", speechRate),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PastelBlue
                        )
                        Text(text = "Cepat (1.2x)", fontSize = 12.sp, color = Color(0xFF546E7A))
                    }

                    Slider(
                        value = speechRate,
                        onValueChange = { onChangeSpeechRate(it) },
                        valueRange = 0.7f..1.2f,
                        steps = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("slider_speech_rate")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    KidButton(
                        text = "Coba Suara Pelafalan 🔊",
                        onClick = onTestSound,
                        backgroundColor = PastelBlue,
                        fontSize = 15,
                        height = 50.dp,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "test_sound_button"
                    )
                }
            }

            // Reset Progres
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Progres Belajar 📊",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = SoftDarkText
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Semua progres tersimpan di memori perangkat secara lokal.",
                        fontSize = 12.sp,
                        color = Color(0xFF78909C)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    KidButton(
                        text = "Mulai Ulang Progres 🔄",
                        onClick = { showResetDialog = true },
                        backgroundColor = CoralRed,
                        fontSize = 15,
                        height = 50.dp,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "reset_progress_button"
                    )
                }
            }

            // Tentang Aplikasi
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Dunia Huruf — Belajar ABC",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftDarkText
                )
                Text(
                    text = "Versi 1.0 • Edukasi Anak Usia 4–6 Tahun",
                    fontSize = 12.sp,
                    color = Color(0xFF90A4AE)
                )
            }
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(text = "Ulangi Progres?", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Apakah kamu yakin ingin mengulang semua bintang dan huruf yang telah dipelajari?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onResetProgress()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = CoralRed)
                ) {
                    Text("Ya, Mulai Ulang", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
