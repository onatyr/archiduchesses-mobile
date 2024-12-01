package fr.onat.turboplant.presentation.plantList

import androidx.lifecycle.ViewModel
import fr.onat.turboplant.data.repositories.PlantRepository

class PlantViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    val plants = plantRepository.getPlants()
}