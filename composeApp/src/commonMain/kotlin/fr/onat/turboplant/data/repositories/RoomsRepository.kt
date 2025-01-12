package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.PlaceDao
import fr.onat.turboplant.data.dao.RoomDao
import fr.onat.turboplant.data.dto.PlaceDto
import fr.onat.turboplant.data.dto.RoomDto
import fr.onat.turboplant.data.entities.toPlace
import fr.onat.turboplant.data.entities.toRoom
import io.ktor.client.call.body
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class RoomsRepository(
    private val archiApi: ArchiApi,
    private val placeDao: PlaceDao,
    private val roomDao: RoomDao
) {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchPlaces().forEach { fetchAllRoomsByPlaceId(it.id) }
        }
    }

    private suspend fun fetchPlaces() =
        (archiApi.get("/places/all")?.body<List<PlaceDto>>() ?: emptyList()).apply {
            placeDao.upsertAll(map { it.toPlace() })
        }

    private suspend fun fetchAllRoomsByPlaceId(placeId: String) =
        (archiApi.get("/places/allRoomsByPlaceId/${placeId}")?.body<List<RoomDto>>()
            ?: emptyList()).apply {
            roomDao.upsertAll(map { it.toRoom() })
        }

    fun getAllPlaces() = placeDao.getAll()

    fun getAllRooms() = roomDao.getAll()
}