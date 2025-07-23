package uk.co.devfoundry.moodselector.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import uk.co.devfoundry.moodselector.analytics.MoodAnalyticsManager
import uk.co.devfoundry.moodselector.domain.TagSelector

class MoodSelectorViewModel(
    private val analytics: MoodAnalyticsManager
) : ViewModel(), TagSelector {

    // Backing flow of the current selection
    private val _selectedMoods = MutableStateFlow<List<String>>(emptyList())
    val selectedMoods: StateFlow<List<String>> = _selectedMoods.asStateFlow()

    // UI-friendly list of all possible moods
    val availableMoods = listOf("Happy", "Sad", "Tired", "Motivated")

    /** Called by the UI when the user taps a mood button */
    fun onMoodSelected(mood: String) {
        selectMood(mood)
    }

    /** TagSelector contract: add a mood to the list if it’s valid and new */
    override fun selectMood(mood: String) {
        if (mood.isBlank()) return

        _selectedMoods.update { current ->
            (current + mood).distinct()
        }
    }

    /** TagSelector contract: read the current list */
    override fun getSelectedMoods(): List<String> =
        selectedMoods.value

    /**
     * Flush the current selections into analytics.
     * Returns whatever the analytics layer outputs (e.g. final stored list).
     */
    fun recordSelections(): List<String> =
        analytics.processMoods(getSelectedMoods())
}
