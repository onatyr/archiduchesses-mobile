package fr.onat.turboplant.presentation.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.data.repositories.PlantsRepository
import fr.onat.turboplant.data.repositories.TasksRepository
import fr.onat.turboplant.models.PlantIdentificationDto
import fr.onat.turboplant.models.PlantbookDetailsDto
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantsViewModel(
    private val plantsRepository: PlantsRepository,
    private val tasksRepository: TasksRepository // todo use
) : ViewModel() {

    val plants = plantsRepository.getPlants()

    private val _newPlant = MutableStateFlow(NewPlantDto())
    val newPlant = _newPlant.asStateFlow()

    private val _searchResult = MutableStateFlow<List<PlantbookDetailsDto>?>(null)
    val searchResult = _searchResult.asStateFlow()

    private val _identificationResult = MutableStateFlow<List<PlantIdentificationDto>?>(null)
    val identificationResult = _identificationResult.asStateFlow()

    fun <T> updateNewPlant(field: NewPlantField<T>, value: String) = field.update(_newPlant, value)

    fun searchExternalPlantByName(value: String) = viewModelScope.launch(Dispatchers.IO) {
        plantsRepository.searchExternalPlantByName(value)?.let { _searchResult.update { it } }
    }

    fun identify(image: ByteArray?, onError: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        _identificationResult.update {
            plantsRepository.identify(
                image = image,
                onError = onError
            )
        }
    }

    fun addNewPlant() = viewModelScope.launch(Dispatchers.IO) {
        val response = plantsRepository.addNewPlant(newPlant.value)
        if (response?.status == HttpStatusCode.OK) _newPlant.update { NewPlantDto() }
    }
}