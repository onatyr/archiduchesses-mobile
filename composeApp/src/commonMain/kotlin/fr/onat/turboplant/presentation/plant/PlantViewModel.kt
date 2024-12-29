package fr.onat.turboplant.presentation.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.data.repositories.PlantRepository
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.PlantbookDetailsDto
import fr.onat.turboplant.models.PlantIdentificationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    val plants = plantRepository.getPlants()

    private val _newPlant = MutableStateFlow(NewPlantDto())
    val newPlant = _newPlant.asStateFlow()

    private val _searchResult = MutableStateFlow<List<PlantbookDetailsDto>?>(null)
    val searchResult = _searchResult.asStateFlow()

    private val _identificationResult = MutableStateFlow<List<PlantIdentificationDto>?>(null)
    val identificationResult = _identificationResult.asStateFlow()

    private val _identificationError = MutableSharedFlow<Unit>()
    val identificationError = _identificationError.asSharedFlow()

    fun <T> updateNewPlant(field: NewPlantField<T>, value: T) = field.update(_newPlant, value)

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
}