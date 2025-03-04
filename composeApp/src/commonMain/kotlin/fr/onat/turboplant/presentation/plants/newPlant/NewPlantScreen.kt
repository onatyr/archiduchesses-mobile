package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.models.dto.NewPlantField
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.libs.utils.LocalSnackbarHostState
import fr.onat.turboplant.libs.utils.onDispose
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.PlantsRoute
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NewPlantScreen(
    viewModel: PlantsViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit
) {

    val newPlant by viewModel.newPlant.collectAsStateWithLifecycle()
    val isNewPlantValid by viewModel.isNewPlantValid.collectAsStateWithLifecycle(false)

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) viewModel.searchExternalPlantByName(searchQuery)

    }

    onDispose { viewModel.resetNewPlant() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(Modifier.size(15.dp))
            SpeciesSearchBar(
                searchQuery = searchQuery,
                updateQuery = viewModel::updateSearchQuery,
                searchResult = searchResult,
                fetchPlantDetails = viewModel::fetchPlantDetails,
                updateSpecies = { viewModel.updateNewPlant(NewPlantField.Species, it) }
            )
            Spacer(Modifier.size(25.dp))
            NameField(
                value = newPlant.name ?: "",
                updateValue = { viewModel.updateNewPlant(NewPlantField.Name, it) },
            )
            SpeciesField(
                value = newPlant.species ?: "",
                updateValue = { viewModel.updateNewPlant(NewPlantField.Species, it) },
            )
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
                value = newPlant.adoptionDate,
                updateValue = { viewModel.updateNewPlant(NewPlantField.AdoptionDate, it) },
            )

            ImageSelectorsRow(
                clearSelectedImage = {},// todo
                clearIdentificationResult = viewModel::resetIdentificationResult
            )
        }
        Column(Modifier.padding(10.dp)) {
            CancelButton(
                onClick = {
                    focusManager.clearFocus()
                    navigate(PlantsRoute)
                }
            )
            Spacer(Modifier.size(10.dp))
            ConfirmButton(
                enabled = isNewPlantValid,
                onClick = {
                    viewModel.addNewPlant(
                        onSuccess = {
                            scope.launch {
                                snackbarHostState.showSnackbar("${newPlant.name} added to your list")
                            }
                        }
                    )
                    focusManager.clearFocus()
                    navigate(PlantsRoute)
                },
                onDisabledClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("At least give it a name ?")
                    }
                }
            )
        }
    }
}