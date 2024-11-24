package fr.onat.turboplant.models

import io.ktor.util.date.GMTDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Plant(
    val name: String,
    val watering: Watering?,
    val sunlight: Sunlight?,
    val adoptionDate: String,
    val roomId: String?,
    val imageUrl: String?
)

enum class Watering() {
    @SerialName("Minimal")
    MINIMAL,

    @SerialName("Sparing")
    SPARING,

    @SerialName("Moderate")
    MODERATE,

    @SerialName("Frequent")
    FREQUENT,
}

enum class Sunlight() {
    @SerialName("Low Light")
    LOW,

    @SerialName("Partial Shade")
    PARTIAL_SHADE,

    @SerialName("Bright Indirect Light")
    BRIGHT_INDIRECT_LIGHT,

    @SerialName("Full Sun")
    FULL_SUN
}

