package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.models.Place
import fr.onat.turboplant.models.Room
import io.ktor.client.call.body

class PlaceRepository(private val archiApi: ArchiApi) {

    suspend fun fetchPlaces() =
        archiApi.get("/place/all")?.body<List<Place>>() ?: emptyList()

    suspend fun fetchAllRoomsByPlaceId(placeId: String) =
        archiApi.get("/place/allRoomsByPlaceId/${placeId}")?.body<List<Room>>() ?: emptyList()
}