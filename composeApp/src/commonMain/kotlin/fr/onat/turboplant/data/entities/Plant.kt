package fr.onat.turboplant.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.dto.Sunlight
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
