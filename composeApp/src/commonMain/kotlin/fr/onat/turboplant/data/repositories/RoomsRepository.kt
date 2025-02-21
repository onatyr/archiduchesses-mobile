package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.PlaceDao
import fr.onat.turboplant.data.dao.RoomDao
import fr.onat.turboplant.data.models.dto.PlaceDto
import fr.onat.turboplant.data.models.dto.RoomDto
import fr.onat.turboplant.data.models.entities.toPlace
import fr.onat.turboplant.data.models.entities.toRoom
import fr.onat.turboplant.libs.extensions.onSuccessAsync
import fr.onat.turboplant.libs.utils.asyncLaunch
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
        asyncLaunch {
            fetchPlaces()
        }
    }

    private suspend fun fetchPlaces() = archiApi.get("/places/all").onSuccessAsync { response ->
        placeDao.upsertAll(
            response.body<List<PlaceDto>>().map { dto ->
                dto.toPlace()
                    .also {
                        fetchAllRoomsByPlaceId(dto.id)
                    }
            }
        )
    }

    private suspend fun fetchAllRoomsByPlaceId(placeId: String) =
        archiApi.get("/places/allRoomsByPlaceId/${placeId}").onSuccessAsync { response ->
            roomDao.upsertAll(
                response.body<List<RoomDto>>().map { dto ->
                    dto.toRoom()
                })
        }

    fun getAllPlaces() = placeDao.getAll()

    fun getAllRooms() = roomDao.getAll()
}