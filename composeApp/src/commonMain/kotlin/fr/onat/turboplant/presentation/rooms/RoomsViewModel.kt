package fr.onat.turboplant.presentation.rooms

import androidx.lifecycle.ViewModel
import fr.onat.turboplant.data.repositories.PlantsRepository
import fr.onat.turboplant.data.repositories.RoomsRepository

class RoomsViewModel(
    private val roomsRepository: RoomsRepository,
    private val plantsRepository: PlantsRepository
) : ViewModel() {
    val places = roomsRepository.getAllPlaces()
    val rooms = roomsRepository.getAllRooms()

}