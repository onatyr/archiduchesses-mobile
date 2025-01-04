package fr.onat.turboplant.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(val id: String, val label: String)