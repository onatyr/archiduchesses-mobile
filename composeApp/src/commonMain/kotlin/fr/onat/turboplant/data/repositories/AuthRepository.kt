package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.database.AppDatabase
import fr.onat.turboplant.data.entities.User
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.models.TokenResponse
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val archiApi: ArchiApi,
    private val database: AppDatabase
) {
    fun isAuthenticated() = database.getUserDao()
        .getAll()
        .map { it.isNotEmpty() && it.first().token != null }

    suspend fun loginRequest(credentials: Credentials) {
        val loginResponse = archiApi.post(
            routeUrl = "/auth/login",
            body = credentials
        )
        if (loginResponse?.status != HttpStatusCode.OK) {
            return
        }
        setToken(loginResponse.body<TokenResponse>().token)
    }

    private suspend fun setToken(token: String) {
        database.getUserDao().upsert(User(name = null, token = token))
    }

    suspend fun clearToken() {
        database.getUserDao().clear()
    }
}