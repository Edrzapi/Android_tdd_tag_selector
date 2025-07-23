package uk.co.devfoundry.moodselector

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.Test
import uk.co.devfoundry.moodselector.ui.theme.screen.MoodSelectorScreen

class MoodSelectorUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun selectingHappyAddsItToSelectedList() {
        composeTestRule.setContent { MoodSelectorScreen() }
        composeTestRule.onNodeWithText("Happy").performClick()
        composeTestRule.onNodeWithText("Happy (Selected)").assertExists()
    }

    @Test
    fun selectingHappyTwiceDoesNotDuplicateIt() {
        composeTestRule.setContent { MoodSelectorScreen() }
        composeTestRule.onNodeWithText("Happy").performClick()
        composeTestRule.onNodeWithText("Happy").performClick()
        composeTestRule.onAllNodesWithText("Happy (Selected)").assertCountEquals(1)
    }

    @Test
    fun selectedMoodsListReflectsAllSelections() {
        composeTestRule.setContent { MoodSelectorScreen() }
        composeTestRule.onNodeWithText("Happy").performClick()
        composeTestRule.onNodeWithText("Tired").performClick()
        composeTestRule.onNodeWithText("Happy (Selected)").assertExists()
        composeTestRule.onNodeWithText("Tired (Selected)").assertExists()
    }

}