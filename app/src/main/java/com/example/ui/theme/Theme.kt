package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ElementXDarkColorScheme = darkColorScheme(
    primary = Teal400,
    onPrimary = Color(0xFF003833),
    primaryContainer = Color(0xFF134E48),
    onPrimaryContainer = Color(0xFF99F6E4),
    secondary = Indigo400,
    onSecondary = Color(0xFF1E1B4B),
    secondaryContainer = Color(0xFF312E81),
    onSecondaryContainer = Color(0xFFE0E7FF),
    tertiary = Emerald400,
    onTertiary = Color(0xFF022C22),
    tertiaryContainer = Color(0xFF064E3B),
    onTertiaryContainer = Color(0xFFA7F3D0),
    background = Zinc950,
    onBackground = Slate100,
    surface = Zinc900,
    onSurface = Slate100,
    surfaceVariant = Zinc800,
    onSurfaceVariant = Slate400,
    outline = BorderMedium,
    outlineVariant = BorderSubtle
)

@Composable
fun ElementXTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ElementXDarkColorScheme,
        typography = Typography,
        content = content
    )
}

