package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.PlantDao
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.entities.toPlant
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class PlantRepository(private val archiApi: ArchiApi, private val plantDao: PlantDao) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            val plants = fetchPlants()
            plantDao.upsertAll(plants.map { it.toPlant() })
        }
    }

    private suspend fun fetchPlants() =
        archiApi.get("/plants/all")?.body<List<PlantDto>>() ?: emptyList()

    suspend fun searchExternalPlantByName(name: String) =
        archiApi.get("/plants/searchExternalPlantByName/$name")?.bodyAsText()

    fun getPlants() = plantDao.getAll()
}