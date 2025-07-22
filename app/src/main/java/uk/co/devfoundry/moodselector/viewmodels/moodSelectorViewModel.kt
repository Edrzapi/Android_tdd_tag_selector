package uk.co.devfoundry.moodselector.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoodSelectorViewModel : ViewModel() {
    private val _selectedMoods = MutableStateFlow<List<String>>(emptyList())
    val selectedMoods: StateFlow<List<String>> = _selectedMoods

    private val allMoods = listOf("Happy", "Tired", "Motivated")
    val moods: List<String> get() = allMoods

    fun selectMood(mood: String) {
        if (mood !in _selectedMoods.value) {
            _selectedMoods.value += mood
        }
    }
}
