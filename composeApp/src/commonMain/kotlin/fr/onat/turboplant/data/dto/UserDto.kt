package fr.onat.turboplant.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String = "hey", // todo remove ?
//    val name: String,
    val token: String
)