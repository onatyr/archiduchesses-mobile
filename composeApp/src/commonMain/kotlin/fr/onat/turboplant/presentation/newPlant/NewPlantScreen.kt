package fr.onat.turboplant.presentation.newPlant

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.presentation.CameraRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.plantList.PlantViewModel
import fr.onat.turboplant.resources.Colors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewPlantScreen(
    viewModel: PlantViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit
) {
    val newPlant by viewModel.newPlant.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    LaunchedEffect(newPlant.species) {
        newPlant.species?.let {
            if (it.length >= 3) viewModel.searchExternalPlantByName(it)
        }
    }

    BoxWithConstraints {
        Column {
            Row {
                TextField(
                    value = newPlant.name ?: "",
                    onValueChange = {
                        viewModel.updateNewPlant(NewPlantField.Name, it)
                    })
                TextField(
                    value = newPlant.species ?: "",
                    onValueChange = {
                        viewModel.updateNewPlant(NewPlantField.Species, it)
                    }
                )
            }
            Text(text = "sunlight")
            TextField(
                value = newPlant.wateringRecurrenceDays.toStringOrNull() ?: "",
                onValueChange = {
                    viewModel.updateNewPlant(NewPlantField.WateringRecurrenceDays, it.toInt())
                }
            )
        }

        ImageIdentificationButton(openCamera = { navigate(CameraRoute) })
    }
}

@Composable
fun BoxScope.ImageIdentificationButton(openCamera: () -> Unit) {
    Button(
        onClick = openCamera,
        modifier = Modifier.size(100.dp).align(Alignment.BottomCenter).padding(20.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.SalmonPink,
            contentColor = Color.Black
        )
    ) {
        Icon(Icons.Default.Create, "add new plant", Modifier.scale(2f))
    }
}