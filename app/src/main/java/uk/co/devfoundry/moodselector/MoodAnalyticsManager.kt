package uk.co.devfoundry.moodselector

/**
 * Handles batch operations on moods, delegating single‑mood
 * selection to a uk.co.devfoundry.moodselector.TagSelector.
 */
class MoodAnalyticsManager(
    private val selector: TagSelector,
    private val logger: MoodLogger) {
    fun processMoods(moods: List<String>): List<String> {
        if (moods.isEmpty()) return emptyList()

        moods
            .filterNot(String::isBlank)     // drop blank entries
            .distinct()                      // prevent dupe
            .forEach { mood ->
                selector.selectMood(mood)
                logger.logMood(mood)
            }

        return selector.getSelectedMoods()
    }
}
