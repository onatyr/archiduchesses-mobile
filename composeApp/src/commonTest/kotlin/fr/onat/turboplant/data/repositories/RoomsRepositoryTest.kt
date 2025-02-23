package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.ArchiApiMock
import fr.onat.turboplant.data.dao.PlaceDao
import fr.onat.turboplant.data.dao.RoomDao
import fr.onat.turboplant.data.dao.UserDao
import io.mockative.any
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class RoomsRepositoryTest {
    private val placeDao = mock(of<PlaceDao>())
    private val roomDao = mock(of<RoomDao>())
    private val userDao = mock(of<UserDao>())

    private var placeResponse = """[{
        "id": "1",
        "label": "Office"
    }]"""

    private var roomResponse = """[{
        "id": "1",
        "label": "Bedroom",
        "placeId": "123",
        "location": "INTERIOR"
    }]"""

    private var roomsRepository =
        RoomsRepository(ArchiApiMock(placeResponse, userDao), placeDao, roomDao)

    private fun setResponseContent(content: String) {
        roomsRepository =
            RoomsRepository(ArchiApiMock(content, userDao), placeDao, roomDao)
    }


    @Test
    fun `fetchPlaces insert call result in local database`() = runBlocking {
        every { userDao.getToken() }.invokes { flowOf("mockToken") }

        setResponseContent(placeResponse)

        roomsRepository.fetchPlaces()

        coVerify { placeDao.upsertAll(any()) }.wasInvoked()
    }

    @Test
    fun `fetchRooms insert call result in local database`() = runBlocking {
        every { userDao.getToken() }.invokes { flowOf("mockToken") }

        setResponseContent(roomResponse)

        roomsRepository.fetchAllRoomsByPlaceId("123")

        coVerify { roomDao.upsertAll(any()) }.wasInvoked()
    }
}