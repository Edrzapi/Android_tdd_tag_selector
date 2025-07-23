package uk.co.devfoundry.moodselector

import java.time.Clock

/**
 * Handles batch operations on moods, delegating single‑mood
 */

class MoodAnalyticsManager(
    private val selector: TagSelector,
    private val logger: MoodLogger,
    private val clock: Clock = Clock.systemUTC()
) {
    fun processMoods(moods: List<String>): List<String> {
        if (moods.isEmpty()) return emptyList()

        val now = clock.instant()
        moods
            .filterNot(String::isBlank)
            .distinct()
            .forEach { mood ->
                selector.selectMood(mood)
                logger.logMood(mood, now)
            }

        return selector.getSelectedMoods()
    }
}