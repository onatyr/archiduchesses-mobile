package fr.onat.turboplant.presentation.plants.plantDetailed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlantDetailedScreen(
    viewModel: PlantsViewModel = koinViewModel(),
    plantId: String
) {
    val plantDetailed by viewModel.getPlantById(plantId).collectAsStateWithLifecycle(null)

    plantDetailed?.let { (plant, roomWithPlace, tasks) ->
        Column {
            PlantDetailedCard(plant)
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                item {
                    SunlightDetailed(sunlight = plant.sunlight)
                }
                item {
                    WateringDetailed(
                        wateringRecurrenceDays = plant.wateringRecurrenceDays,
                        latestWateringOccurrence = tasks.maxByOrNull { it.dueDate.epochSeconds }?.dueDate
                    )
                }
                item {
                    LocationDetailed(
                        roomWithPlace = roomWithPlace
                    )
                }
                item {
                    SmoothGreyBox(Modifier.fillMaxWidth().aspectRatio(1f))
                }
            }
            HistoryDetailed(modifier = Modifier.weight(1f), tasks = tasks)
            PlantDetailedFooter()
        }
    }
}

@Composable
fun PlantDetailedFooter() {
    Row(modifier = Modifier.height(65.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = {}) {
            Text("Delete")
        }
        Button(onClick = {}) {
            Text("Edit")
        }
    }
}