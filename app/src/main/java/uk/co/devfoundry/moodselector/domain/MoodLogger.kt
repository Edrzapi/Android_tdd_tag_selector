package uk.co.devfoundry.moodselector.domain

import java.time.Instant
/** Records a single mood selection along with a timestamp. */
interface MoodLogger {
  fun logMood(mood: String, timestamp: Instant)
}
