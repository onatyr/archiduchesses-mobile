package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

@Serializable
data class Room(val id: String, val label: String, val placeId: String, val location: RoomLocation)

enum class RoomLocation {
    INTERIOR,
    EXTERIOR
}