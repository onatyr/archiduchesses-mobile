package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(
    val email: String,
    val password: String,
)

@Serializable
data class TokenResponse(
    val token: String
)