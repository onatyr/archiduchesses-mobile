package fr.onat.turboplant.data.repositories

import androidx.compose.ui.util.fastFlatMap
import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.PlantDao
import fr.onat.turboplant.data.dao.TaskDao
import fr.onat.turboplant.data.dto.NewPlantDto
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.entities.Plant
import fr.onat.turboplant.data.entities.Task
import fr.onat.turboplant.data.entities.toPlant
import fr.onat.turboplant.data.entities.toTask
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.PlantIdentificationDto
import fr.onat.turboplant.models.PlantbookEntityDto
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class PlantsRepository(
    private val archiApi: ArchiApi,
    private val plantDao: PlantDao,
    private val taskDao: TaskDao
) {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchPlants().forEach { plantDto ->
                taskDao.upsertAll(plantDto.tasks.map { it.toTask() })
            }
        }
    }

    private suspend fun fetchPlants() =
        (archiApi.get("/plants/all")?.body<List<PlantDto>>() ?: emptyList()).apply {
            plantDao.upsertAll(map { dto -> dto.toPlant() })
        }


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