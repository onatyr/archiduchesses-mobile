package fr.onat.turboplant.data.models.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import fr.onat.turboplant.data.models.dto.PlantDto
import fr.onat.turboplant.data.models.dto.Sunlight
import kotlinx.datetime.Instant

@Entity
data class Plant(
    @PrimaryKey val id: String,
    val name: String,
    val species: String?,
    val wateringRecurrenceDays: Int?,
    val sunlight: Sunlight?,
    val adoptionDate: Instant,
    val roomId: String?,
    val imageUrl: String?
)

data class PlantWithRoom(
    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "id"
    ) val room: Room?,
)

data class PlantDetailed(
    @Embedded val plant: Plant,
    @Relation(
        entity = Room::class,
        parentColumn = "roomId",
        entityColumn = "id"
    ) val roomWithPlace: RoomWithPlace?,
    @Relation(
        parentColumn = "id",
        entityColumn = "plantId"
    )
    val tasks: List<Task>
)

fun PlantDto.toPlant() = Plant(
    id = id,
    name = name,
    species = species,
    wateringRecurrenceDays = wateringRecurrenceDays,
    sunlight = sunlight,
    adoptionDate = adoptionDate,
    roomId = roomId,
    imageUrl = imageUrl
)
