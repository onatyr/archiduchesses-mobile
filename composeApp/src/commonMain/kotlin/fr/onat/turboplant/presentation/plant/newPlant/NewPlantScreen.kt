package fr.onat.turboplant.presentation.plant.newPlant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import fr.onat.turboplant.data.dto.Sunlight
import fr.onat.turboplant.libs.extensions.collectAsEffect
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.presentation.CameraRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.plant.PlantViewModel
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.adoption_date
import turboplant.composeapp.generated.resources.sunlight
import turboplant.composeapp.generated.resources.watering_recurrence

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

    Box(Modifier.padding(15.dp).background(Colors.PlantCardGreen, RoundedCornerShape(15.dp))) {
        Column(
            Modifier.padding(10.dp).fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(5.dp))
        ) {
            NewPlantCardHeader(
                newPlant = newPlant,
                updateName = { viewModel.updateNewPlant(NewPlantField.Name, it) },
                updateSpecies = { viewModel.updateNewPlant(NewPlantField.Species, it) },
                onIconClick = { navigate(CameraRoute) }
            )
            Spacer(Modifier.height(10.dp))
            NewPlantTextField(
                value = newPlant.wateringRecurrenceDays.toStringOrNull() ?: "",
                onValueChange = {
                    viewModel.updateNewPlant(
                        NewPlantField.WateringRecurrenceDays,
                        it.toInt()
                    )
                },
                placeHolderText = stringResource(Res.string.watering_recurrence)
            )
            NewPlantTextField(
                value = newPlant.sunlight?.textValue ?: "",
                onValueChange = {
                    viewModel.updateNewPlant(
                        NewPlantField.Sunlight,
                        Sunlight.LOW // todo change it with select fields
                    )
                },
                placeHolderText = stringResource(Res.string.sunlight)
            )
            NewPlantTextField(
                value = newPlant.adoptionDate ?: "",
                onValueChange = {
                    viewModel.updateNewPlant(
                        NewPlantField.AdoptionDate,
                        it
                    )
                },
                placeHolderText = stringResource(Res.string.adoption_date)
            )
        }

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