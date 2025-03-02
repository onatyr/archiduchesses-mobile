package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.models.PlantbookDetailsDto
import fr.onat.turboplant.data.models.PlantbookEntityDto
import fr.onat.turboplant.data.models.dto.NewPlantField
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.libs.logger.logger
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.composables.BaseTextField
import fr.onat.turboplant.presentation.composables.SelectField
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import org.koin.compose.viewmodel.koinViewModel
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.search_plant_by_species

@Composable
fun NewPlantScreen(
    viewModel: PlantsViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit
) {
    val newPlant by viewModel.newPlant.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
    logger(searchResult)

    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) viewModel.searchExternalPlantByName(searchQuery)

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SpeciesSearchBar(
                searchQuery = searchQuery,
                updateQuery = viewModel::updateSearchQuery,
                searchResult = searchResult
            )
            NameField(
                value = newPlant.name ?: "",
                updateValue = { viewModel.updateNewPlant(NewPlantField.Name, it) },
            )
            SpeciesField(
                value = newPlant.species ?: "",
                updateValue = { viewModel.updateNewPlant(NewPlantField.Species, it) },
            )
            Spacer(Modifier.height(10.dp))
            WateringRecurrenceField(
                value = newPlant.wateringRecurrenceDays.toStringOrNull() ?: "",
                updateValue = {
                    if (it.isNotEmpty()) it.toIntOrNull() ?: return@WateringRecurrenceField
                    viewModel.updateNewPlant(NewPlantField.WateringRecurrenceDays, it)
                }
            )
            SunlightField(
                value = newPlant.sunlight?.textValue ?: "",
                updateValue = { index ->
                    viewModel.updateNewPlant(
                        NewPlantField.Sunlight,
                        Sunlight.entries[index].textValue
                    )
                }
            )

            DateField(
                value = newPlant.adoptionDate.toString(), // todo use date picker
                updateValue = { viewModel.updateNewPlant(NewPlantField.AdoptionDate, it) },
            )
            Button(
                onClick = { viewModel.addNewPlant(); focusManager.clearFocus() }
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, "")
            }
        }
    }
}

@Composable
fun SpeciesSearchBar(
    searchQuery: String,
    updateQuery: (String) -> Unit,
    searchResult: List<PlantbookEntityDto>
) {
    SelectField(
        selectableOptions = searchResult,
        onSelectIndexed = {},
        content = {
            BaseTextField(
                value = searchQuery,
                updateValue = updateQuery,
                placeHolderRes = Res.string.search_plant_by_species
            )
        },
        dropdownContent = { plant ->
            PlantbookSearchResult(plant)
        }
    )
}

@Composable
fun PlantbookSearchResult(plant: PlantbookEntityDto) {
    Text(plant.displayPid)
}