package fr.onat.turboplant.presentation.plantList

import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddNewPlantScreen(
    viewModel: PlantViewModel = koinViewModel(),
) {
    SearchBar
}