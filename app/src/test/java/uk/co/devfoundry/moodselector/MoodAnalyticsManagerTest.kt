package uk.co.devfoundry.moodselector.analytics

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.co.devfoundry.moodselector.domain.MoodLogger
import uk.co.devfoundry.moodselector.domain.TagSelector
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@RunWith(MockitoJUnitRunner::class)
class MoodAnalyticsManagerTest {

    @Mock private lateinit var tagSelector: TagSelector
    @Mock private lateinit var moodLogger: MoodLogger

    private val expectedTimestamp: Instant =
        Instant.parse("2025-07-23T12:00:00Z")
    private val fixedClock: Clock =
        Clock.fixed(expectedTimestamp, ZoneOffset.UTC)

    private lateinit var subject: MoodAnalyticsManager

    @Before
    fun setUp() {
        subject = MoodAnalyticsManager(tagSelector, moodLogger, fixedClock)
    }

    @Test
    fun shouldReturnEmptyListWhenInputIsEmpty() {
        val result = subject.recordSelections(emptyList())
        assertEquals(emptyList<String>(), result)

        // no logging for empty input
        verify(moodLogger, never()).logMood(any(), any())
    }

    @Test
    fun shouldReturnSelectorStateForValidInput() {
        whenever(tagSelector.getSelectedMoods())
            .thenReturn(listOf("Happy", "Sad"))

        val result = subject.recordSelections(listOf("Happy", "Sad"))
        assertEquals(listOf("Happy", "Sad"), result)
    }

    @Test
    fun shouldFilterOutBlankAndDuplicateMoods() {
        whenever(tagSelector.getSelectedMoods())
            .thenReturn(listOf("Happy"))

        val result = subject.recordSelections(listOf("Happy", "", "Happy"))
        assertEquals(listOf("Happy"), result)
    }

    @Test
    fun shouldLogEachDistinctMoodWithTimestampInOrder() {
        val input = listOf("Happy", "Sad", "Happy", "Tired")
        whenever(tagSelector.getSelectedMoods())
            .thenReturn(listOf("Happy", "Sad", "Tired"))

        subject.recordSelections(input)

        val order = inOrder(tagSelector, moodLogger)
        order.verify(tagSelector).selectMood("Happy")
        order.verify(moodLogger).logMood("Happy", expectedTimestamp)

        order.verify(tagSelector).selectMood("Sad")
        order.verify(moodLogger).logMood("Sad", expectedTimestamp)

        order.verify(tagSelector).selectMood("Tired")
        order.verify(moodLogger).logMood("Tired", expectedTimestamp)
    }

    @Test
    fun shouldSkipBlankMoodsAndNotLogThem() {
        subject.recordSelections(listOf("", "Happy"))

        verify(moodLogger).logMood("Happy", expectedTimestamp)
        verify(moodLogger, never()).logMood("", expectedTimestamp)
    }

    @Test
    fun shouldReturnEmptyWhenSelectorRemainsEmpty() {
        whenever(tagSelector.getSelectedMoods()).thenReturn(emptyList())

        val result = subject.recordSelections(listOf("A", "B", "C"))
        assertEquals(emptyList<String>(), result)
    }
}
