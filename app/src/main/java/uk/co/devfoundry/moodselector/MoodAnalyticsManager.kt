package uk.co.devfoundry.moodselector

/**
 * Handles batch operations on moods, delegating single‑mood
 * selection to a uk.co.devfoundry.moodselector.TagSelector.
 */
class MoodAnalyticsManager(
    private val selector: TagSelector
) {

    fun processMoods(moods: List<String>): List<String> {
        if (moods.isEmpty()) return emptyList()
        moods.distinct().forEach { selector.selectMood(it) }
        return selector.getSelectedMoods()
    }
}
