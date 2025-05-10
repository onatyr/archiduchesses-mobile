package fr.onat.turboplant.presentation.plants.plantDetailed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.data.models.entities.Plant
import fr.onat.turboplant.data.models.entities.RoomWithPlace
import fr.onat.turboplant.data.models.entities.Task
import fr.onat.turboplant.libs.extensions.getDisplayableDayCount
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.resources.Colors
import kotlinx.datetime.Instant
import org.jetbrains.compose.resources.painterResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.droplet_icon
import turboplant.composeapp.generated.resources.house_icon
import turboplant.composeapp.generated.resources.sun_icon

@Composable
fun PlantDetailedCard(plant: Plant) {
    SmoothGreyBox {
        Row(
            modifier = Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = plant.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                plant.species?.let { species ->
                    Text(
                        text = species,
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp
                    )
                }
            }
            AsyncImage(
                model = plant.imageUrl,
                contentDescription = "Image representing a ${plant.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).background(
                    Color.Black, RoundedCornerShape(10.dp)
                )
            )
        }
    }
}

@Composable
fun SunlightDetailed(sunlight: Sunlight?) {
    SmoothGreyBox(Modifier.fillMaxWidth().aspectRatio(1f)) {
        Icon(
            painter = painterResource(Res.drawable.sun_icon),
            tint = Colors.SunshineLight,
            contentDescription = null,
            modifier = Modifier.size(70.dp)
        )
        Text(text = sunlight?.textValue ?: "No information about sunlight")
    }
}

@Composable
fun WateringDetailed(wateringRecurrenceDays: Int?, latestWateringOccurrence: Instant?) {
    SmoothGreyBox(Modifier.fillMaxWidth().aspectRatio(1f)) {
        Icon(
            painter = painterResource(Res.drawable.droplet_icon),
            tint = Colors.DropletBlue,
            contentDescription = null,
            modifier = Modifier.size(70.dp)
        )
        Text(
            text = wateringRecurrenceDays?.let { "Every $it days" }
                ?: "No information about watering"
        )
        Text(text = latestWateringOccurrence?.let { it.getDisplayableDayCount() } ?: "")
    }
}

@Composable
fun LocationDetailed(roomWithPlace: RoomWithPlace?) {
    SmoothGreyBox(Modifier.fillMaxWidth().aspectRatio(1f)) {
        roomWithPlace?.let { (room, place) ->
            Icon(
                painter = painterResource(Res.drawable.house_icon),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Text(room.label)
            Text(place.label)
        }
    }
}

@Composable
fun HistoryDetailed(modifier: Modifier = Modifier, tasks: List<Task>) {
    SmoothGreyBox((modifier.fillMaxWidth())) {

    }
}