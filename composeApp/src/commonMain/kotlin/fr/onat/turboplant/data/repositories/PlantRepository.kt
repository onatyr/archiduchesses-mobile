package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.models.Plant
import io.ktor.client.call.body

class PlantRepository(private val archiApi: ArchiApi) {
    suspend fun fetchPlants() = archiApi.get("/plants/all")?.body<List<Plant>>() ?: emptyList()
}