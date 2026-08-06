package com.portfoliox.app.features.skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliox.app.data.model.Skill
import com.portfoliox.app.data.repository.SkillsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SkillsUiState(
    val isLoading: Boolean = true,
    // category name -> skills in that category, in a sensible display order
    val grouped: List<Pair<String, List<Skill>>> = emptyList(),
    val error: String? = null
)

private val CATEGORY_ORDER = listOf(
    "PROGRAMMING", "ANDROID_DEVELOPMENT", "WEB_DEVELOPMENT", "UI_UX", "TOOLS", "SOFT_SKILLS"
)

private fun categoryLabel(raw: String): String = when (raw.uppercase()) {
    "PROGRAMMING" -> "Programming"
    "ANDROID_DEVELOPMENT" -> "Android Development"
    "WEB_DEVELOPMENT" -> "Web Development"
    "UI_UX" -> "UI/UX"
    "TOOLS" -> "Tools"
    "SOFT_SKILLS" -> "Soft Skills"
    else -> raw
}

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsRepository: SkillsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillsUiState())
    val uiState: StateFlow<SkillsUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            skillsRepository.getSkills()
                .onSuccess { skills ->
                    val grouped = skills
                        .groupBy { it.category.uppercase() }
                        .toSortedMap(compareBy { category ->
                            CATEGORY_ORDER.indexOf(category).let { if (it == -1) Int.MAX_VALUE else it }
                        })
                        .map { (category, list) -> categoryLabel(category) to list }
                    _uiState.value = SkillsUiState(isLoading = false, grouped = grouped)
                }
                .onFailure { e ->
                    _uiState.value = SkillsUiState(isLoading = false, error = e.message)
                }
        }
    }
}
