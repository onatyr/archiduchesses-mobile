package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.entities.User
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import io.mockative.once
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthRepositoryTest {

    private val archiApi = mock(of<ArchiApi>())
    private val userDao = mock(of<UserDao>())
    private val authRepository = AuthRepository(archiApi, userDao)

    @Test
    fun `isAuthenticated returns true when user has a token`() {
        every { userDao.getAll() } returns flowOf(listOf(User(id = "id", token = "token")))
        val isAuthenticated = authRepository.isAuthenticated()
        runBlocking {
            assertTrue(isAuthenticated.first())
        }
    }

    @Test
    fun `isAuthenticated returns false when there is no user`() {
        every { userDao.getAll() } returns flowOf(emptyList())
        val isAuthenticated = authRepository.isAuthenticated()
        runBlocking {
            assertFalse(isAuthenticated.first())
        }
    }

    @Test
    fun `clearToken clears user data`() {
        runBlocking {
            coEvery { userDao.clear() }

            authRepository.clearToken()

            coVerify { userDao.clear() }.wasInvoked(exactly = once)
        }
    }

//    @Test
//    fun `loginRequest successfully upsert new user`() {
//        runBlocking {
//            val userDto = UserDto(id = "id", token = "token")
//            val credentials = Credentials(email = "email", password = "password")
//
//            val successfulResponse = mock(of<HttpResponse>())
//            every { successfulResponse.status } returns HttpStatusCode.OK
////            coEvery { successfulResponse.body<UserDto>() } returns userDto
//
//            coEvery { archiApi.post("/auth/login", credentials) } returns successfulResponse
//
//            coEvery { userDao.upsert(any()) } returns Unit
//
//            authRepository.loginRequest(credentials)
//
//            coVerify { userDao.upsert(userDto.toUser()) }.wasInvoked()
//        }
//    }
}