package fr.onat.turboplant.presentation.plantList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.repositories.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {
    val plants = plantRepository.getPlants()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResult = MutableStateFlow("")
    val searchResult = _searchResult.asStateFlow()

    fun updateSearchQuery(value: String) {
        _searchQuery.update { value }
        if (searchQuery.value.length >= 3) searchExternalPlantByName()
    }

    private fun searchExternalPlantByName() {
        viewModelScope.launch {
            _searchResult.update {
                plantRepository.searchExternalPlantByName(searchQuery.value) ?: ""
            }
        }
    }
}