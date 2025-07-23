package uk.co.devfoundry.moodselector.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag        // <- add this import
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel

@Composable
fun MoodSelectorScreen(
    viewModel: MoodSelectorViewModel = viewModel()
) {
    val selectedMoods by viewModel.selectedMoods.collectAsState()
    val moods = viewModel.moods

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mood buttons
        moods.forEach { mood ->
            Button(
                onClick = { viewModel.selectMood(mood) },
                enabled = mood !in selectedMoods,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("button-$mood")          // ← tag each button
                    .padding(vertical = 4.dp)
            ) {
                Text(text = mood)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Selected moods header
        Text(
            text = "Selected Moods:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.testTag("selected-moods-label")  // ← tag the label
        )

        // Display each selected mood
        selectedMoods.forEach { mood ->
            Text(
                text = "$mood (Selected)",
                modifier = Modifier.testTag("selected-$mood")      // optional, only if you want to tag each entry
            )
        }
    }
}
