package fr.onat.turboplant.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.Plant
import fr.onat.turboplant.data.repositories.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantListViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    private val _plants = MutableStateFlow(emptyList<Plant>())
    val plants = _plants.asStateFlow()

    fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val plants = plantRepository.fetchPlants()
                _plants.update { plants }
            } catch (e: Exception) {
                logger(e.message)
            }
        }
    }
}