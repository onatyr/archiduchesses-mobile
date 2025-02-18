package fr.onat.turboplant.data.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(val id: String, val label: String)