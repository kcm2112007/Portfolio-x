package com.portfoliox.app.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onToggleTheme: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenSkills: () -> Unit,
    onOpenProjects: () -> Unit,
    onOpenContact: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PortfolioX") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(Icons.Filled.DarkMode, contentDescription = "Toggle theme")
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Couldn't load profile: ${state.error}", textAlign = TextAlign.Center)
                }
            }
            else -> {
                val profile = state.profile
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = profile?.photoUrl,
                        contentDescription = "Profile photo",
                        modifier = Modifier.size(120.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(profile?.name ?: "Your Name", style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        profile?.bio ?: "Short bio goes here",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        profile?.socialLinks?.keys?.forEach { platform ->
                            Text(platform, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedButton(onClick = { /* open profile?.resumeUrl via Intent */ }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Filled.Download, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Download Resume")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(onClick = onOpenAbout, modifier = Modifier.fillMaxWidth()) {
                        Text("About Me")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(onClick = onOpenSkills, modifier = Modifier.fillMaxWidth()) {
                        Text("Skills")
                    }
                }
            }
        }
    }
}
