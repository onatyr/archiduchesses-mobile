package fr.onat.turboplant.presentation.plants.plantList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import fr.onat.turboplant.data.entities.PlantWithRoom
import fr.onat.turboplant.libs.extensions.formatToString
import fr.onat.turboplant.libs.extensions.toStringWithUnit
import fr.onat.turboplant.presentation.AddNewPlantRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.calendar_emoji
import turboplant.composeapp.generated.resources.day
import turboplant.composeapp.generated.resources.days
import turboplant.composeapp.generated.resources.droplet_emoji
import turboplant.composeapp.generated.resources.sun_emoji

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
                PlantCard(plant)
            }
        }
        NewPlantButton(
            onClick = { navigate(AddNewPlantRoute) },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        )
    }
}

@Composable
fun PlantCard(plantWithRoom: PlantWithRoom) {
    val (plant, room) = plantWithRoom
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
            room?.let { room ->
                TextWithLeadingContent(
                    leadingContent = { },
                    text = room.label
                )
            }

            Text(
                text = plant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            plant.species?.let { species ->
                Text(
                    text = species,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.size(10.dp))

            plant.sunlight?.let { sunlight ->
                TextWithLeadingContent(
                    leadingContent = { Text(stringResource(Res.string.sun_emoji)) },
                    text = sunlight.textValue
                )
            }
            plant.wateringRecurrenceDays?.let { wateringRecurrenceDays ->
                TextWithLeadingContent(
                    leadingContent = { Text(stringResource(Res.string.droplet_emoji)) },
                    text = "Every ${
                        wateringRecurrenceDays.toStringWithUnit(
                            singular = Res.string.day.key,
                            plural = Res.string.days.key
                        )
                    }"
                )
            }
            TextWithLeadingContent(
                leadingContent = { Text(stringResource(Res.string.calendar_emoji)) },
                text = "Adopted on ${plant.adoptionDate.formatToString()}"
            )
        }
    }
}

@Composable
fun TextWithLeadingContent(
    leadingContent: @Composable () -> Unit,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        leadingContent()
        Spacer(Modifier.size(2.dp))
        Text(text = text)
    }
}

@Composable
fun NewPlantButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.size(45.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.SalmonPink,
            contentColor = Color.Black
        )
    ) {
        Icon(Icons.Default.Add, "add new plant", Modifier.scale(2f))
    }
}