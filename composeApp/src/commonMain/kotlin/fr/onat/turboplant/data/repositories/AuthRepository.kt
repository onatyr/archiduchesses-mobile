package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.dto.UserDto
import fr.onat.turboplant.data.entities.toUser
import fr.onat.turboplant.libs.extensions.onFailure
import fr.onat.turboplant.libs.extensions.onSuccess
import fr.onat.turboplant.models.LoginDetails
import fr.onat.turboplant.models.RegistrationDetails
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AuthRepository(
    private val archiApi: ArchiApi,
    private val userDao: UserDao
) {
    fun isAuthenticated() = userDao.getToken()
        .map { !it.isNullOrEmpty() }

    suspend fun loginRequest(
        loginDetails: LoginDetails
    ) {
        archiApi.post(routeUrl = "/auth/login", body = loginDetails)
            .onSuccess {
                CoroutineScope(Dispatchers.IO).launch {
                    setUser(it.body<UserDto>())
                }
            }
    }

    suspend fun registrationRequest(
        registrationDetails: RegistrationDetails,
        onSuccess: () -> Unit,
        onFailure: (HttpResponse?) -> Unit
    ) {
        archiApi
            .post(routeUrl = "/auth/register", body = registrationDetails)
            .onSuccess { onSuccess() }
            .onFailure { onFailure(it) }
    }

    private suspend fun setUser(userDto: UserDto) = userDao.upsert(userDto.toUser())

    suspend fun clearToken() {
        userDao.clear()
    }
}