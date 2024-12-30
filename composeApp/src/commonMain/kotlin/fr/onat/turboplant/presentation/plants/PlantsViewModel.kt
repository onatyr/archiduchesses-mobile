package fr.onat.turboplant.presentation.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.data.repositories.PlantRepository
import fr.onat.turboplant.data.repositories.TaskRepository
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.PlantIdentificationDto
import fr.onat.turboplant.models.PlantbookDetailsDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantsViewModel(
    private val plantRepository: PlantRepository,
    private val taskRepository: TaskRepository // todo use
) : ViewModel() {

    val plants = plantRepository.getPlants()

    private val _newPlant = MutableStateFlow(NewPlantDto())
    val newPlant = _newPlant.asStateFlow()

    private val _searchResult = MutableStateFlow<List<PlantbookDetailsDto>?>(null)
    val searchResult = _searchResult.asStateFlow()

    private val _identificationResult = MutableStateFlow<List<PlantIdentificationDto>?>(null)
    val identificationResult = _identificationResult.asStateFlow()

    fun <T> updateNewPlant(field: NewPlantField<T>, value: String) = field.update(_newPlant, value)

    fun searchExternalPlantByName(value: String) = viewModelScope.launch(Dispatchers.IO) {
        plantRepository.searchExternalPlantByName(value)?.let { _searchResult.update { it } }
    }

    fun identify(image: ByteArray?, onError: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        _identificationResult.update {
            plantRepository.identify(
                image = image,
                onError = onError
            )
        }
    }

    fun addNewPlant() = viewModelScope.launch(Dispatchers.IO) {
        val response = plantRepository.addNewPlant(newPlant.value)
        logger(response?.status)
        _newPlant.update { NewPlantDto() }
    }
}