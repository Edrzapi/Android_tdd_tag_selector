package uk.co.devfoundry.moodselector.viewmodels

import uk.co.devfoundry.moodselector.domain.TagSelector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MoodSelectorViewModel : ViewModel(), TagSelector {

    // Backing StateFlow to represent the current selection of moods in a reactive way.
    private val _selectedMoods = MutableStateFlow<List<String>>(emptyList())
    val selectedMoods: StateFlow<List<String>> = _selectedMoods

    // Predefined list of all moods
    val moods = listOf("Happy", "Sad", "Tired", "Motivated")

    /**
     * Selects a mood if it’s not already in the list.
     * - We check `isNotBlank()` to avoid empty or whitespace-only entries.
     * - We use `_selectedMoods.value + mood` to produce a new list,
     *   preserving immutability for StateFlow updates.
     */
    override fun selectMood(mood: String) {
        if (mood.isNotBlank() && !_selectedMoods.value.contains(mood)) {
            _selectedMoods.value += mood
        }
    }

    /**
     * Returns the current list of selected moods.
     * Exposed by the uk.co.devfoundry.moodselector.domain.TagSelector interface for use in batch analytics.
     */
    override fun getSelectedMoods(): List<String> =
        _selectedMoods.value
}