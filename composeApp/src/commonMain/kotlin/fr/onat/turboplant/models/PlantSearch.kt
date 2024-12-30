package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

@Serializable
data class PlantbookEntityDto(
    val pid: String,
    val displayPid: String
)

@Serializable
data class PlantbookDetailsDto(
    val pid: String,
    val displayPid: String,
    val maxLightLux: Int,
    val minLightLux: Int,
    val maxSoilMoist: Int,
    val minSoilMoist: Int,
    val imageUrl: String,
)

@Serializable
data class PlantIdentificationDto(
    val plantnetName: String,
    val plantnetGenus: String,
    val score: Double,
    val plantbookDetailsDto: PlantbookDetailsDto?
)