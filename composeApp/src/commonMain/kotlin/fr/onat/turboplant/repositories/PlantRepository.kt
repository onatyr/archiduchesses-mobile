package fr.onat.turboplant.repositories

import fr.onat.turboplant.api.ArchiApi

class PlantRepository(private val archiApi: ArchiApi) {
    suspend fun fetchPlants() = archiApi.fetchPlants()
}