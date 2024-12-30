package fr.onat.turboplant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.dto.Sunlight
import kotlinx.datetime.Instant

@Entity
data class Plant(
    @PrimaryKey val id: String,
    val name: String,
    val wateringRecurrenceDays: Int?,
    val sunlight: Sunlight?,
    val adoptionDate: Instant,
    val roomId: String?,
    val imageUrl: String?
)

fun PlantDto.toPlant() = Plant(
    id = id,
    name = name,
    wateringRecurrenceDays = wateringRecurrenceDays,
    sunlight = sunlight,
    adoptionDate = adoptionDate,
    roomId = roomId,
    imageUrl = imageUrl
)
