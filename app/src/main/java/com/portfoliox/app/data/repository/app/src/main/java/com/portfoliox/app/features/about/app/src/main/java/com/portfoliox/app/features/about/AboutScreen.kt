package com.portfoliox.app.features.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.portfoliox.app.data.model.EducationEntry

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Me") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
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
                    Text("Couldn't load About info: ${state.error}", textAlign = TextAlign.Center)
                }
            }
            else -> {
                val profile = state.profile
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        Column {
                            Text("Introduction", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.padding(top = 4.dp))
                            Text(
                                profile?.bio ?: "Add a short introduction in the profiles table.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    item {
                        Column {
                            Text("Career Objective", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.padding(top = 4.dp))
                            Text(
                                profile?.careerObjective ?: "Add your career objective in the profiles table.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    item {
                        Text("Education", style = MaterialTheme.typography.titleLarge)
                    }

                    if (state.education.isEmpty()) {
                        item {
                            Text(
                                "No education entries yet — add rows to education_entries in Supabase.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        items(state.education) { entry ->
                            EducationCard(entry)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EducationCard(entry: EducationEntry) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(entry.degree, style = MaterialTheme.typography.titleMedium)
            Text(entry.institution, style = MaterialTheme.typography.bodyMedium)
            val range = listOfNotNull(entry.startDate, entry.endDate).joinToString(" – ")
            if (range.isNotBlank()) {
                Text(range, style = MaterialTheme.typography.bodyMedium)
            }
            entry.score?.let {
                Text("Score: $it", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
