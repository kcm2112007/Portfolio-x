package com.portfoliox.app.core.ui.theme

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

private val PortfolioBlue = Color(0xFF2F6FED)
private val PortfolioBlueDark = Color(0xFF89B4FF)

private val LightColors = lightColorScheme(
    primary = PortfolioBlue,
    secondary = Color(0xFF5C6BC0),
    background = Color(0xFFFAFAFC),
    surface = Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = PortfolioBlueDark,
    secondary = Color(0xFF9FA8DA),
    background = Color(0xFF101114),
    surface = Color(0xFF1A1B1F)
)

@Composable
fun PortfolioXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PortfolioXTypography,
        content = content
    )
}
