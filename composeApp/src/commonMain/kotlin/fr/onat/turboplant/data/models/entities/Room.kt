package fr.onat.turboplant.data.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import fr.onat.turboplant.data.models.dto.RoomDto
import fr.onat.turboplant.data.models.dto.RoomLocation

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Place::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("placeId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Room(
    @PrimaryKey val id: String,
    val label: String,
    val placeId: String,
    val location: RoomLocation
)

data class RoomWithPlace(
    @Embedded val room: Room,
    @Relation(
        parentColumn = "placeId",
        entityColumn = "id"
    ) val place: Place,
)

fun RoomDto.toRoom() = Room(id = id, label = label, placeId = placeId, location = location)