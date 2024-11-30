package fr.onat.turboplant.presentation.plantList

import androidx.lifecycle.ViewModel
import fr.onat.turboplant.data.repositories.PlantRepository

class PlantListViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    val plants = plantRepository.getPlants()
}