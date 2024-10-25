package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

@Serializable
data class Plant(val name: String, val watering: Watering?, val sunlight: Sunlight?)

enum class Watering {
    LOW,
    MEDIUM,
    HIGH
}

enum class Sunlight {
    LOW,
    MEDIUM,
    HIGH
}

