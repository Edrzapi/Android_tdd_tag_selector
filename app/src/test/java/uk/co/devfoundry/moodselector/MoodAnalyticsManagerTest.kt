package uk.co.devfoundry.moodselector
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
import uk.co.devfoundry.moodselector.analytics.MoodAnalyticsManager
import uk.co.devfoundry.moodselector.domain.MoodLogger
import uk.co.devfoundry.moodselector.domain.TagSelector
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@RunWith(MockitoJUnitRunner::class)
class MoodAnalyticsManagerTest {

    @Mock private lateinit var selector: TagSelector
    @Mock private lateinit var logger: MoodLogger

    private val fixedInstant = Instant.parse("2025-07-23T12:00:00Z")
    private val clock = Clock.fixed(fixedInstant, ZoneOffset.UTC)

    private lateinit var manager: MoodAnalyticsManager

    @Before
    fun setUp() {
        // now pass the clock as well
        manager = MoodAnalyticsManager(selector, logger, clock)
    }

    @Test
    fun shouldReturnEmptyListWhenInputIsEmpty() {
        val result = manager.processMoods(emptyList())
        assertEquals(emptyList<String>(), result)
        // also ensure no logging happened
        verify(logger, org.mockito.kotlin.never()).logMood(any(), any())
    }

    @Test
    fun shouldReturnSelectorResultForNonEmptyInput() {
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy", "Sad"))

        val result = manager.processMoods(listOf("Happy", "Sad"))

        assertEquals(listOf("Happy", "Sad"), result)
    }

    @Test
    fun shouldFilterInvalidMoodsCorrectly() {
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy"))

        val result = manager.processMoods(listOf("Happy", "Invalid"))

        assertEquals(listOf("Happy"), result)
    }

    @Test
    fun shouldLogEachDistinctMoodWithTimestampInOrder() {
        val input = listOf("Happy", "Sad", "Happy", "Tired")
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy", "Sad", "Tired"))

        manager.processMoods(input)

        val order = inOrder(selector, logger)
        order.verify(selector).selectMood("Happy")
        order.verify(logger).logMood("Happy", fixedInstant)

        order.verify(selector).selectMood("Sad")
        order.verify(logger).logMood("Sad", fixedInstant)

        order.verify(selector).selectMood("Tired")
        order.verify(logger).logMood("Tired", fixedInstant)
    }

    @Test
    fun shouldReturnEmptyWhenSelectorRemainsEmpty() {
        whenever(selector.getSelectedMoods()).thenReturn(emptyList())

        val result = manager.processMoods(listOf("A", "B", "C"))
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun shouldSkipBlankMoodsAndNotLogThem() {
        manager.processMoods(listOf("", "Happy"))
        verify(logger).logMood("Happy", fixedInstant)
        verify(logger, never()).logMood("", fixedInstant)
    }

}
