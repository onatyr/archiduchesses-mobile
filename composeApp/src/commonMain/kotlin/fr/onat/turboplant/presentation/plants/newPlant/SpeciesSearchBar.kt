package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.onat.turboplant.data.models.PlantbookDetailsDto
import fr.onat.turboplant.data.models.PlantbookEntityDto
import fr.onat.turboplant.presentation.composables.BaseTextField
import fr.onat.turboplant.presentation.composables.SelectField
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import fr.onat.turboplant.resources.Colors
import kotlinx.coroutines.launch
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.search_plant_by_species

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