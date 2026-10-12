package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KidColorScheme = lightColorScheme(
    primary = PastelBlue,
    onPrimary = Color.White,
    primaryContainer = PastelSky,
    onPrimaryContainer = SoftDarkText,
    secondary = PastelOrange,
    onSecondary = Color.White,
    secondaryContainer = PastelOrangeLight,
    onSecondaryContainer = SoftDarkText,
    tertiary = PastelGreen,
    onTertiary = Color.White,
    tertiaryContainer = PastelGreenLight,
    onTertiaryContainer = SoftDarkText,
    background = SoftBackdrop,
    onBackground = SoftDarkText,
    surface = Color.White,
    onSurface = SoftDarkText,
    surfaceVariant = PastelYellowLight,
    onSurfaceVariant = SoftDarkText,
    error = CoralRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Tetap gunakan palet ceria anak yang konsisten
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KidColorScheme,
        typography = Typography,
        content = content
    )
}
