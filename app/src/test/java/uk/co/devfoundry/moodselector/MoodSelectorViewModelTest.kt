package uk.co.devfoundry.moodselector

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import uk.co.devfoundry.moodselector.domain.MoodDataSource
import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel
import uk.co.devfoundry.moodselector.viewmodels.UiState
import java.io.IOException

class MoodSelectorViewModelTest {

    private lateinit var testDispatcher: StandardTestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadMoods_emitsSuccessAfterDataFetched() = runTest {
        // Given: a fake data source with delay (simulate async)
        val fakeDataSource = object : MoodDataSource {
            override suspend fun getMoods(): List<String> {
                delay(1000) // simulate network delay
                return listOf("Happy", "Sad", "Tired", "Motivated")
            }
        }
        val vm = MoodSelectorViewModel(
            dataSource = fakeDataSource,
            dispatcher = testDispatcher
        )

        vm.loadMoods()
        advanceUntilIdle() // fast-forward the coroutine until all tasks are done

        val state = vm.state.value
        assertTrue(state is UiState.Success)
        assertEquals(listOf("Happy", "Sad", "Tired", "Motivated"), (state as UiState.Success).moods)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loadMoods_emitsErrorOnException() = runTest {
        val fakeErrorSource = object : MoodDataSource {
            override suspend fun getMoods(): List<String> =
                throw IOException("data load failed")
        }
        val vm = MoodSelectorViewModel(
            dataSource = fakeErrorSource,
            dispatcher = testDispatcher
        )

        vm.loadMoods()
        advanceUntilIdle()

        val state = vm.state.value
        assertTrue(state is UiState.Error)
        assertEquals("data load failed", (state as UiState.Error).message)
    }

}