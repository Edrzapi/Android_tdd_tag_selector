// src/main/kotlin/uk/co/devfoundry/moodselector/analytics/MoodAnalyticsManager.kt
package uk.co.devfoundry.moodselector.analytics

import uk.co.devfoundry.moodselector.domain.TagSelector
import uk.co.devfoundry.moodselector.domain.MoodLogger
import java.time.Clock

/**
 * Takes raw selections, filters & de-dupes them,
 * delegates selectMood() + logs each with timestamp,
 * then returns the selector’s final state.
 */
class MoodAnalyticsManager(
    private val selector: TagSelector,
    private val logger: MoodLogger,
    private val clock: Clock = Clock.systemUTC()
) {
    fun recordSelections(raw: List<String>): List<String> {
        if (raw.isEmpty()) return emptyList()

        val now = clock.instant()
        raw
            .filterNot(String::isBlank)
            .distinct()
            .forEach { mood ->
                selector.selectMood(mood)
                logger.logMood(mood, now)
            }

        return selector.getSelectedMoods()
    }
}
