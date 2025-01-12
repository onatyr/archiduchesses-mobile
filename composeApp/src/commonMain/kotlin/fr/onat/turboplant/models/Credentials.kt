package fr.onat.turboplant.models

import kotlinx.serialization.Serializable

interface Credentials

@Serializable
data class LoginDetails(
    val email: String = "",
    val password: String = "",
) : Credentials

@Serializable
data class RegistrationDetails(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmationPassword: String = ""
) : Credentials

@Serializable
data class TokenResponse(
    val token: String
)