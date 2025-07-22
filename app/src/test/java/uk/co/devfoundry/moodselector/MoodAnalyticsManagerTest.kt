package uk.co.devfoundry.moodselector

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoodAnalyticsManagerTest {

    @Mock
    lateinit var selector: TagSelector

    private lateinit var manager: MoodAnalyticsManager

    @Before
    fun setUp() {
        manager = MoodAnalyticsManager(selector)
    }

    @Test
    fun emptyInputReturnsEmptyList() {
        // Given no moods
        val result = manager.processMoods(emptyList())

        // Then result is empty
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun nonEmptyInputReturnsSelectorResult() {
        // Given selector will return exactly this list after selection
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy", "Sad"))

        // When processing a non‑empty list
        val result = manager.processMoods(listOf("Happy", "Sad"))

        // Then we get back whatever the selector holds
        assertEquals(listOf("Happy", "Sad"), result)
    }

    @Test
    fun filtersInvalidMoodsCorrectly() {
        // Simulate selecting moods but only "Happy" ends up in state
        whenever(selector.getSelectedMoods()).thenReturn(listOf("Happy"))

        // When processing mixed list
        val result = manager.processMoods(listOf("Happy", "Invalid"))

        // Only "Happy" survives
        assertEquals(listOf("Happy"), result)
    }

    @Test
    fun processMoodsDelegatesToSelectMoodForEachUniqueMood() {
        // Given a list with duplicates
        val input = listOf("Happy", "Sad", "Happy", "Tired")

        // When
        manager.processMoods(input)

        // Then selectMood is called once per unique mood
        verify(selector).selectMood("Happy")
        verify(selector).selectMood("Sad")
        verify(selector).selectMood("Tired")
    }

    @Test
    fun processMoodsReturnsEmptyWhenSelectorRemainsEmpty() {
        // Even if we pass values, if selector.getSelectedMoods() is empty,
        // result should be empty
        whenever(selector.getSelectedMoods()).thenReturn(emptyList())

        val result = manager.processMoods(listOf("A", "B", "C"))
        assertEquals(emptyList<String>(), result)
    }
}
