package uk.co.devfoundry.moodselector

import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel

class MoodAnalyticsManager(
    private val selector: MoodSelectorViewModel
) {

    fun processMoods(moods: List<String>): List<String> {
        if (moods.isEmpty()) return emptyList()

        moods.distinct().forEach { mood ->
            selector.selectMood(mood)
        }
        return selector.selectedMoods.value
    }
}


