package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import fr.onat.turboplant.data.models.PlantbookDetailsDto
import fr.onat.turboplant.data.models.PlantbookEntityDto
import fr.onat.turboplant.data.models.dto.NewPlantField
import fr.onat.turboplant.data.models.dto.Sunlight
import fr.onat.turboplant.libs.extensions.toStringOrNull
import fr.onat.turboplant.libs.utils.onDispose
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.PlantsRoute
import fr.onat.turboplant.presentation.composables.BaseTextField
import fr.onat.turboplant.presentation.composables.SelectField
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import fr.onat.turboplant.resources.Colors
import kotlinx.coroutines.launch
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

    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) viewModel.searchExternalPlantByName(searchQuery)

    }

    onDispose { viewModel.resetNewPlant() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
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
            Button(
                onClick = {
                    viewModel.addNewPlant()
                    focusManager.clearFocus()
                    navigate(PlantsRoute)
                }
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
    searchResult: List<PlantbookEntityDto>,
    fetchPlantDetails: suspend (String) -> PlantbookDetailsDto?,
    updateSpecies: (String) -> Unit
) {
    val isExpended = remember { mutableStateOf(false) }
    LaunchedEffect(searchResult) {
        if (searchResult.isNotEmpty()) isExpended.value = true
    }

    SelectField(
        selectableOptions = searchResult,
        onSelectIndexed = { updateSpecies(searchResult[it].displayPid) },
        isExpended = isExpended,
        content = {
            BaseTextField(
                value = searchQuery,
                updateValue = updateQuery,
                borderColor = Colors.SupraGreen,
                placeHolderRes = Res.string.search_plant_by_species,
                leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.White) }
            )
        },
        dropdownContent = { plant ->
            Box(Modifier.background(Colors.BlackGround).padding(5.dp).width(300.dp)) {
                PlantbookSearchResult(plant = plant, fetchPlantDetails = fetchPlantDetails)
            }
        }
    )
}

@Composable
fun PlantbookSearchResult(
    plant: PlantbookEntityDto,
    fetchPlantDetails: suspend (String) -> PlantbookDetailsDto?
) {
    val scope = rememberCoroutineScope()
    var plantDetails by remember { mutableStateOf<PlantbookDetailsDto?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            plantDetails = fetchPlantDetails(plant.pid)
        }
    }

    SmoothGreyBox(Modifier.fillMaxWidth().height(150.dp)) {
        Text(
            text = plant.displayPid,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
        Box {
            plantDetails?.let { details ->
                AsyncImage(
                    model = details.imageUrl,
                    contentDescription = "Image representing a ${plant.displayPid}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.background(
                        Color.Black, RoundedCornerShape(10.dp)
                    )
                )
            }
        }
    }
}