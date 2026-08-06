package com.portfoliox.app.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Stand-in for feature screens not yet built (About, Skills, Projects, etc).
 * Replace each usage in PortfolioXNavGraph with the real screen as it's built.
 */
@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$title — coming soon", style = MaterialTheme.typography.titleMedium)
    }
}
