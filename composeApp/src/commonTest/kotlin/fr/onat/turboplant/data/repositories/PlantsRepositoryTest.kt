package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.ArchiApiMock
import fr.onat.turboplant.data.dao.PlantDao
import fr.onat.turboplant.data.dao.TaskDao
import fr.onat.turboplant.data.dao.UserDao
import io.mockative.any
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class PlantsRepositoryTest {
    private val plantDao = mock(of<PlantDao>())
    private val userDao = mock(of<UserDao>())
    private val taskDao = mock(of<TaskDao>())

    private var plantResponse = """[{
        "id": "1",
        "name": "Rose",
        "tasks": [],
        "species": null,
        "wateringRecurrenceDays": null,
        "sunlight": null,
        "adoptionDate": "2025-02-22T12:34:56.789Z",
        "roomId": null,
        "imageUrl": null
    }]"""

    private var plantsRepository =
        PlantsRepository(ArchiApiMock(plantResponse, userDao), plantDao, taskDao)


    @Test
    fun `fetchPlants insert call result in local database`() = runBlocking {
        every { userDao.getToken() }.invokes { flowOf("mockToken") }

        plantsRepository.fetchPlants()

        coVerify { plantDao.upsertAll(any()) }.wasInvoked()
    }

}