package fr.onat.turboplant.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.Plant
import fr.onat.turboplant.repositories.PlaceRepository
import fr.onat.turboplant.repositories.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantListViewModel(
    private val plantRepository: PlantRepository,
    private val placeRepository: PlaceRepository
) : ViewModel() {
    private val _plants = MutableStateFlow(emptyList<Plant>())
    val plants = _plants.asStateFlow()

    fun fetch() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                logger(placeRepository.fetchPlaces())
                logger(placeRepository.fetchAllRoomsByPlaceId("f89bf62f-0216-44d1-a347-250c44a8384b"))
                val plants = plantRepository.fetchPlants()

                _plants.update { plants }
            } catch (e: Exception) {

            }
        }
    }
}