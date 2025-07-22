package uk.co.devfoundry.moodselector

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel

@RunWith(MockitoJUnitRunner::class)
class MoodAnalyticsManagerTest {

    private lateinit var analyticsManager: MoodAnalyticsManager

    @Before
    fun setUp() {
        val selector = MoodSelectorViewModel()
        analyticsManager = MoodAnalyticsManager(selector)
    }

    @Test
    fun `returns empty list when input is empty`() {
        val result = analyticsManager.processMoods(emptyList())
        assertEquals(emptyList<String>(), result)
    }
}
