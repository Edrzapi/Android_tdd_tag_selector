package uk.co.devfoundry.moodselector

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.devfoundry.moodselector.ui.theme.screen.MoodSelectorScreen
import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel

@RunWith(AndroidJUnit4::class)
class MoodSelectorScreenTest {

  @get:Rule
  val composeRule = createComposeRule()

  private val moods = listOf("Happy", "Sad", "Tired", "Motivated")

  @Test
  fun initialStateRendersAllButtonsEnabledAndEmptySelection() {
    composeRule.setContent {
      MoodSelectorScreen(viewModel = MoodSelectorViewModel())
    }

    // All mood‑buttons exist and are enabled
    moods.forEach { mood ->
      composeRule
        .onNodeWithTag("button-$mood")
        .assertIsEnabled()
    }

    // Label exists, but no "(Selected)" entries
    composeRule
      .onNodeWithTag("selected-moods-label")
      .assertExists()
    composeRule
      .onAllNodesWithText("(Selected)")
      .assertCountEquals(0)
  }

  @Test
  fun selectingMoodDisablesButtonAndDisplaysInList() {
    composeRule.setContent {
      MoodSelectorScreen(viewModel = MoodSelectorViewModel())
    }

    // Click “Happy”
    composeRule
      .onNodeWithTag("button-Happy")
      .performClick()

    // Button is now disabled…
    composeRule
      .onNodeWithTag("button-Happy")
      .assertIsNotEnabled()

    // …and “Happy (Selected)” appears
    composeRule
      .onNodeWithText("Happy (Selected)")
      .assertExists()
  }

  @Test
  fun selectingMoodTwiceDoesNotDuplicate() {
    composeRule.setContent {
      MoodSelectorScreen(viewModel = MoodSelectorViewModel())
    }

    // Click “Sad” twice
    composeRule.onNodeWithTag("button-Sad").performClick()
    composeRule.onNodeWithTag("button-Sad").performClick()

    // Only one “Sad (Selected)”
    composeRule
      .onAllNodesWithText("Sad (Selected)")
      .assertCountEquals(1)
  }

  @Test
  fun selectingMultipleMoodsReflectsAll() {
    composeRule.setContent {
      MoodSelectorScreen(viewModel = MoodSelectorViewModel())
    }

    // Select “Happy” and “Tired”
    composeRule.onNodeWithTag("button-Happy").performClick()
    composeRule.onNodeWithTag("button-Tired").performClick()

    // Both selections appear
    composeRule.onNodeWithText("Happy (Selected)").assertExists()
    composeRule.onNodeWithText("Tired (Selected)").assertExists()
  }

  @Test
  fun selectingAllMoodsDisablesAllButtons() {
    composeRule.setContent {
      MoodSelectorScreen(viewModel = MoodSelectorViewModel())
    }

    // Click every mood
    moods.forEach { mood ->
      composeRule.onNodeWithTag("button-$mood").performClick()
    }

    // Now all buttons should be disabled
    moods.forEach { mood ->
      composeRule
        .onNodeWithTag("button-$mood")
        .assertIsNotEnabled()
    }
  }
}
