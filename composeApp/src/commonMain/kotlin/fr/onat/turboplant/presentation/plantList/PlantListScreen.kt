package fr.onat.turboplant.presentation.plantList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import fr.onat.turboplant.data.entities.Plant
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.sunlight
import turboplant.composeapp.generated.resources.watering

@Composable
fun PlantListScreen(
    viewModel: PlantListViewModel = koinViewModel<PlantListViewModel>()
) {
    val plants by viewModel.plants.collectAsStateWithLifecycle(emptyList())
    LazyColumn(Modifier.padding(vertical = 10.dp).background(Color.Black)) {
        items(plants) { plant ->
            PlantCard(plant)
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .background(Color.Green.copy(0.1f), RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = plant.imageUrl,
            contentDescription = "Image representing a ${plant.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp).background(
                Color.Black, RoundedCornerShape(10.dp)
            )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = plant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
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