package fr.onat.turboplant.repositories

import fr.onat.turboplant.api.ArchiApi
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.models.TokenResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class AuthRepository(private val archiApi: ArchiApi) {
    suspend fun loginRequest(credentials: Credentials, onResponse: (Boolean) -> Unit) {
        val loginResponse = archiApi.post(
            routeUrl = "/auth/login",
            body = credentials,
            onError = { onResponse(false) }
        )
        if (loginResponse?.status != HttpStatusCode.OK) {
            onResponse(false)
            return
        }
        archiApi.setToken(loginResponse.body<TokenResponse>().token)
        onResponse(true)
    }
}