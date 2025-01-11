package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.dto.UserDto
import fr.onat.turboplant.data.entities.toUser
import fr.onat.turboplant.models.Credentials
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val archiApi: ArchiApi,
    val userDao: UserDao
) {
    fun isAuthenticated() = userDao
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
        setUser(loginResponse.body<UserDto>())
    }

    private suspend fun setUser(userDto: UserDto) {
        userDao.upsert(userDto.toUser())
    }

    suspend fun clearToken() {
        userDao.clear()
    }
}