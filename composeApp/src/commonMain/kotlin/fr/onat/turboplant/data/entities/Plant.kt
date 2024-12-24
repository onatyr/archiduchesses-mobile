package fr.onat.turboplant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.onat.turboplant.data.dto.PlantDto
import fr.onat.turboplant.data.dto.Sunlight
import fr.onat.turboplant.data.dto.Watering

@Entity
data class Plant(
    @PrimaryKey val id: String,
    val name: String,
    val watering: Watering?,
    val sunlight: Sunlight?,
    val adoptionDate: String,
    val roomId: String?,
    val imageUrl: String?
)

fun PlantDto.toPlant() = Plant(
    id = id,
    name = name,
    watering = watering,
    sunlight = sunlight,
    adoptionDate = adoptionDate,
    roomId = roomId,
    imageUrl = imageUrl
)
