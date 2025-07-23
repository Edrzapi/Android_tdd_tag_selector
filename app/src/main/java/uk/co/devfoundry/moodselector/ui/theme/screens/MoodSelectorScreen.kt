package uk.co.devfoundry.moodselector.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import uk.co.devfoundry.moodselector.viewmodels.MoodSelectorViewModel

@Composable
fun MoodSelectorScreen(
    viewModel: MoodSelectorViewModel = viewModel())
{
    val selectedMoods by viewModel.selectedMoods.collectAsState()
    val moods = viewModel.selectedMoods

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mood buttons
        selectedMoods.forEach { selectedMoods ->
            Button(
                onClick = { viewModel.selectMood(selectedMoods) },
                enabled = selectedMoods !in selectedMoods,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = selectedMoods)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Selected moods
        Text(
            text = "Selected Moods:",
            style = MaterialTheme.typography.titleMedium
        )
        // Display each selected mood
        selectedMoods.forEach { mood ->
            Text(text = "$mood (Selected)")
        }
    }
}
