package fr.onat.turboplant.presentation.plantList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.NewPlantField
import fr.onat.turboplant.data.dto.Sunlight
import fr.onat.turboplant.data.repositories.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    val plants = plantRepository.getPlants()

    private val _newPlant = MutableStateFlow(NewPlantDto())
    val newPlant = _newPlant.asStateFlow()

    private val _searchResult = MutableStateFlow("")
    val searchResult = _searchResult.asStateFlow()

    fun <T> updateNewPlant(field: NewPlantField<T>, value: T) = field.update(_newPlant, value)

    fun searchExternalPlantByName(value: String) {
        viewModelScope.launch {
            _searchResult.update {
                plantRepository.searchExternalPlantByName(value) ?: ""
            }
        }
    }
}