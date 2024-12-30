package fr.onat.turboplant.data.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDto(
    val id: String,
    val name: String,
    val wateringRecurrenceDays: Int?,
    val sunlight: Sunlight?,
    val adoptionDate: Instant,
    val roomId: String?,
    val imageUrl: String?,
    val tasks: List<TaskDto>
)

enum class Sunlight(val textValue: String) {
    @SerialName("Low Light")
    LOW("Low Light"),

    @SerialName("Partial Shade")
    PARTIAL_SHADE("Partial Shade"),

    @SerialName("Bright Indirect Light")
    BRIGHT_INDIRECT_LIGHT("Bright Indirect Light"),

    @SerialName("Full Sun")
    FULL_SUN("Full Sun")
}

fun String?.toSunlightEnumOrNull() = Sunlight.entries.firstOrNull { it.textValue == this }