package com.portfoliox.app.features.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliox.app.data.model.EducationEntry
import com.portfoliox.app.data.model.UserProfile
import com.portfoliox.app.data.repository.EducationRepository
import com.portfoliox.app.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AboutUiState(
    val isLoading: Boolean = true,
    val profile: UserProfile? = null,
    val education: List<EducationEntry> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val educationRepository: EducationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val profileResult = profileRepository.getProfile()
            val educationResult = educationRepository.getEducation()

            if (profileResult.isFailure) {
                _uiState.value = AboutUiState(isLoading = false, error = profileResult.exceptionOrNull()?.message)
                return@launch
            }

            _uiState.value = AboutUiState(
                isLoading = false,
                profile = profileResult.getOrNull(),
                education = educationResult.getOrDefault(emptyList())
            )
        }
    }
}
