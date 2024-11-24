package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

@Serializable
data class Place(val id: String, val label: String)