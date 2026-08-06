package com.portfoliox.app.features.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {
    val destination by viewModel.destination.collectAsState()

    val nameAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.6f) }

    LaunchedEffect(Unit) {
        logoScale.animateTo(1f, animationSpec = tween(durationMillis = 500))
        nameAlpha.animateTo(1f, animationSpec = tween(durationMillis = 600))
    }

    LaunchedEffect(destination) {
        if (destination is SplashDestination.Home) {
            onNavigateHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .height(96.dp)
                    .graphicsLayer(scaleX = logoScale.value, scaleY = logoScale.value)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "PortfolioX logo",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "PortfolioX",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.graphicsLayer(alpha = nameAlpha.value)
            )
        }
    }
}
