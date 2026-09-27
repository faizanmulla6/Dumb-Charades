package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AccentGold,
    onPrimary = DarkIndigo,
    primaryContainer = CardBackgroundElevated,
    onPrimaryContainer = AccentGold,
    secondary = ElectricCyan,
    onSecondary = Color.White,
    secondaryContainer = CardBackgroundDark,
    onSecondaryContainer = ElectricCyan,
    tertiary = NeonGreen,
    onTertiary = DarkIndigo,
    background = DarkIndigo,
    onBackground = TextPrimary,
    surface = DeepPurple,
    onSurface = TextPrimary,
    surfaceVariant = CardBackgroundDark,
    onSurfaceVariant = TextSecondary,
    error = NeonCoral,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme(
    // We intentionally keep an electric party theme for Dumb Charades so it looks vibrant in all lighting conditions
    primary = AccentGold,
    onPrimary = DarkIndigo,
    primaryContainer = CardBackgroundElevated,
    onPrimaryContainer = AccentGold,
    secondary = ElectricCyan,
    onSecondary = Color.White,
    secondaryContainer = CardBackgroundDark,
    onSecondaryContainer = ElectricCyan,
    tertiary = NeonGreen,
    onTertiary = DarkIndigo,
    background = DarkIndigo,
    onBackground = TextPrimary,
    surface = DeepPurple,
    onSurface = TextPrimary,
    surfaceVariant = CardBackgroundDark,
    onSurfaceVariant = TextSecondary,
    error = NeonCoral,
    onError = Color.White
)

@Composable
fun DumbCharadesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep custom game palette for authentic party atmosphere
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
