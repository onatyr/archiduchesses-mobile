package fr.onat.turboplant.presentation.plants.plantList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.presentation.AddNewPlantRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlantListScreen(
    viewModel: PlantsViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit,
) {
    val plants by viewModel.plants.collectAsStateWithLifecycle(emptyList())
    Box {
        LazyColumn(
            Modifier
                .padding(vertical = 10.dp)
                .background(Color.Black)
                .fillMaxSize()
        ) {
            items(plants) { plant ->
                PlantCard(plantWithRoom = plant, openDetailedCard = { navigate(it) })
            }
        }
        NewPlantButton(
            onClick = { navigate(AddNewPlantRoute) },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )
    }
}