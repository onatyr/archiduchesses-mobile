package fr.onat.turboplant.data.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(val id: String, val label: String, val placeId: String, val location: RoomLocation)

enum class RoomLocation {
    INTERIOR,
    EXTERIOR
}