package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.models.TokenResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthRepository(private val archiApi: ArchiApi) {
    val isAuthenticated = archiApi.isAuthenticated

    suspend fun loginRequest(credentials: Credentials) {
        val loginResponse = archiApi.post(
            routeUrl = "/auth/login",
            body = credentials
        )
        if (loginResponse?.status != HttpStatusCode.OK) {
            return
        }
        archiApi.setToken(loginResponse.body<TokenResponse>().token)
    }
}