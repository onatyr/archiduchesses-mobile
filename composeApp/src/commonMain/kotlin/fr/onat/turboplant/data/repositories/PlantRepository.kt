package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.PlantDao
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.entities.toPlant
import fr.onat.turboplant.models.PlantIdentificationDto
import fr.onat.turboplant.models.PlantbookEntityDto
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
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

    suspend fun identify(image: ByteArray?, onError: () -> Unit) = image?.let {
        archiApi.post(
            routeUrl = "/plants/identify",
            body = MultiPartFormDataContent(
                formData {
                    append("files", image, Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"plant_image.jpeg\"")
                    })
                },
                boundary = "WebAppBoundary"
            ),
            onError = onError
        )?.body<List<PlantIdentificationDto>?>()
    }

    suspend fun searchExternalPlantByName(name: String) =
        archiApi.get("/plants/searchExternalPlantByName/$name")?.body<List<PlantbookEntityDto>>()

    suspend fun addNewPlant(newPlant: NewPlantDto) = archiApi.post("/plants/add", body = newPlant)

    fun getPlants() = plantDao.getAll()
}