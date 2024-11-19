package fr.onat.turboplant.repositories

import fr.onat.turboplant.api.ArchiApi
import io.ktor.client.statement.bodyAsText

class PlaceRepository(private val archiApi: ArchiApi) {

    suspend fun fetchPlaces() =
        archiApi.get("/place/all")?.bodyAsText()

    suspend fun fetchAllRoomsByPlaceId(placeId: String) =
        archiApi.get("/place/allRoomsByPlaceId/${placeId}")?.bodyAsText()
}