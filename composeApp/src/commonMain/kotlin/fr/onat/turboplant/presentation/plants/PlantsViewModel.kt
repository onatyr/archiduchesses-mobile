package fr.onat.turboplant.presentation.plants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.models.dto.NewPlantDto
import fr.onat.turboplant.data.models.dto.NewPlantField
import fr.onat.turboplant.data.repositories.PlantsRepository
import fr.onat.turboplant.data.repositories.TasksRepository
import fr.onat.turboplant.libs.extensions.onSuccess
import fr.onat.turboplant.libs.logger.logger
import fr.onat.turboplant.data.models.PlantIdentificationDto
import fr.onat.turboplant.data.models.PlantbookDetailsDto
import fr.onat.turboplant.data.models.PlantbookEntityDto
import fr.onat.turboplant.libs.extensions.asyncLaunch
import fr.onat.turboplant.libs.extensions.onSuccessAsync
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

    val plants = plantsRepository.getPlantsWithRoom()

    private val _newPlant = MutableStateFlow(NewPlantDto())
    val newPlant = _newPlant.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResult = MutableStateFlow<List<PlantbookEntityDto>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _identificationResult = MutableStateFlow<List<PlantIdentificationDto>?>(null)
    val identificationResult = _identificationResult.asStateFlow()

    fun resetNewPlant() = _newPlant.update { NewPlantDto() }

    fun <T> updateNewPlant(field: NewPlantField<T>, value: String) = field.update(_newPlant, value)

    fun getPlantById(id: String) = plantsRepository.getPlantById(id)

    fun updateSearchQuery(searchQuery: String) = _searchQuery.update { searchQuery }

    fun searchExternalPlantByName(name: String) = asyncLaunch {
        plantsRepository.searchExternalPlantByName(
            name = name,
            onResult = { _searchResult.emit(it) }
        )
    }

    suspend fun fetchPlantDetails(plantPid: String) = plantsRepository.fetchPlantDetails(plantPid)

    fun identify(image: ByteArray?, onError: () -> Unit) = asyncLaunch {
        _identificationResult.update {
            plantsRepository.identify(
                image = image,
                onError = onError
            )
        }
    }

    fun addNewPlant() = asyncLaunch {
        plantsRepository.addNewPlant(newPlant.value).onSuccessAsync {
            plantsRepository.fetchPlants()
            _newPlant.update { NewPlantDto() }
        }
    }
}