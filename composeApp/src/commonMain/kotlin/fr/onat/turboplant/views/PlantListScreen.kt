package fr.onat.turboplant.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.models.Plant
import fr.onat.turboplant.viewModels.PlantListViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import turboplant.composeapp.generated.resources.*

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PlantListScreen(
    viewModel: PlantListViewModel = koinViewModel<PlantListViewModel>()
) {
    viewModel.fetch()
    val plants by viewModel.plants.collectAsStateWithLifecycle()
    LazyColumn(Modifier.padding(vertical = 10.dp)) {
        items(plants) { plant ->
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                PlantCard(plant)
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .background(Color.Green.copy(0.1f), RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = plant.name,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${stringResource(Res.string.watering)}: ${plant.watering}",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${stringResource(Res.string.sunlight)}: ${plant.sunlight}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}