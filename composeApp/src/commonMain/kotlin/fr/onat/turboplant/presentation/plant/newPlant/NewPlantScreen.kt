package fr.onat.turboplant.presentation.plant.newPlant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.data.dto.Sunlight
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.presentation.CameraRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.composables.SelectField
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
                    it.toIntOrNull() ?: return@NewPlantTextField
                    viewModel.updateNewPlant(NewPlantField.WateringRecurrenceDays, it)
                },
                placeHolderText = stringResource(Res.string.watering_recurrence)
            )
            SelectField(
                selectableOptions = Sunlight.entries.map { it.textValue },
                onSelectIndexed = { index ->
                    viewModel.updateNewPlant(
                        NewPlantField.Sunlight,
                        Sunlight.entries[index].textValue
                    )
                }
            ) {
                NewPlantTextField(
                    value = newPlant.sunlight?.textValue ?: "",
                    onValueChange = {},
                    placeHolderText = stringResource(Res.string.sunlight),
                    enabled = false
                )
            }
            NewPlantTextField(
                value = newPlant.adoptionDate ?: "",
                onValueChange = { viewModel.updateNewPlant(NewPlantField.AdoptionDate, it) },
                placeHolderText = stringResource(Res.string.adoption_date)
            )
        }

    }
}