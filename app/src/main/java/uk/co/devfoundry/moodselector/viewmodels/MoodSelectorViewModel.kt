package uk.co.devfoundry.moodselector.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.devfoundry.moodselector.domain.MoodDataSource
import uk.co.devfoundry.moodselector.domain.TagSelector

sealed class UiState {
    object Loading : UiState()
    data class Success(val moods: List<String>) : UiState()
}

class MoodSelectorViewModel(
    private val dataSource: MoodDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), TagSelector {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val _selectedMoods = MutableStateFlow<List<String>>(emptyList())
    val selectedMoods: StateFlow<List<String>> = _selectedMoods

    val moods = listOf("Happy", "Sad", "Tired", "Motivated")

    fun loadMoods() {
        _state.value = UiState.Loading
        viewModelScope.launch(dispatcher) {
            val moods = dataSource.getMoods()
            _state.value = UiState.Success(moods)
        }
    }

    override fun selectMood(mood: String) {
        if (mood.isNotBlank() && !_selectedMoods.value.contains(mood)) {
            _selectedMoods.value += mood
        }
    }
    override fun getSelectedMoods(): List<String> = _selectedMoods.value
}
