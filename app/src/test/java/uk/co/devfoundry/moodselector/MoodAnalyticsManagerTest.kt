package uk.co.devfoundry.moodselector

import uk.co.devfoundry.moodselector.TagSelector
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoodAnalyticsManagerTest {

    @Mock private lateinit var selector: TagSelector
    @Mock private lateinit var logger: MoodLogger

    private lateinit var manager: MoodAnalyticsManager

    @Before
    fun setUp() {
        manager = MoodAnalyticsManager(selector, logger)
    }

    @Test
    fun shouldReturnEmptyListWhenInputIsEmpty() {
        val result = manager.processMoods(emptyList())
        assertEquals(emptyList<String>(), result)
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
    fun shouldDelegateToSelectMoodAndLogEachUniqueMood() {
        val input = listOf("Happy", "Sad", "Happy", "Tired")
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy", "Sad", "Tired"))

        manager.processMoods(input)

        // verify the exact interleaving of calls in order
        val order = inOrder(selector, logger)
        order.verify(selector).selectMood("Happy")
        order.verify(logger).logMood("Happy")

        order.verify(selector).selectMood("Sad")
        order.verify(logger).logMood("Sad")

        order.verify(selector).selectMood("Tired")
        order.verify(logger).logMood("Tired")
    }

    @Test
    fun shouldReturnEmptyWhenSelectorRemainsEmpty() {
        whenever(selector.getSelectedMoods()).thenReturn(emptyList())

        val result = manager.processMoods(listOf("A", "B", "C"))

        assertEquals(emptyList<String>(), result)
    }
}
