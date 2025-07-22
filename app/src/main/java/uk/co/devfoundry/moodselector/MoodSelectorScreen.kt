package uk.co.devfoundry.moodselector

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoodSelectorScreen() {

    val moods = listOf("Happy", "Tired", "Motivated")
    val selectedMoods = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Mood buttons
        moods.forEach { mood ->
            Button(
                onClick = {
                    if (!selectedMoods.contains(mood)) {
                        selectedMoods.add(mood)
                    }
                },
                enabled = !selectedMoods.contains(mood),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text = mood)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // selected moods
        Text(
            text = "Selected Moods:",
            style = MaterialTheme.typography.titleMedium
        )
        selectedMoods.forEach { mood ->
            Text(text = "$mood (Selected)")
        }
    }
}
