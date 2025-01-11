package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.dto.UserDto
import fr.onat.turboplant.data.entities.User
import fr.onat.turboplant.data.entities.toUser
import fr.onat.turboplant.models.Credentials
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockative.any
import io.mockative.coEvery
import io.mockative.coVerify
import io.mockative.every
import io.mockative.mock
import io.mockative.of
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
    fun `loginRequest successfully upsert new user`() {
        runBlocking {
            val userDto = UserDto(id = "id", token = "token")
            val credentials = Credentials(email = "email", password = "password")

            val successfulResponse = mock(of<HttpResponse>())
            every { successfulResponse.status } returns HttpStatusCode.BadRequest
            coEvery { successfulResponse.body<UserDto>() } returns userDto

            coEvery { archiApi.post("/auth/login", credentials) } returns successfulResponse

            coEvery { userDao.upsert(any()) } returns Unit

            authRepository.loginRequest(credentials)

            coVerify { userDao.upsert(userDto.toUser()) }
        }
    }
//
//    @Test
//    fun `loginRequest does nothing on failed login`() = runBlocking {
//        val credentials = Credentials(username = "test", password = "password")
//
//        // Mock the API response with a failure
//        val response = mockk<HttpResponse> {
//            every { status } returns HttpStatusCode.Unauthorized
//        }
//        coEvery { archiApi.post("/auth/login", credentials) } returns response
//
//        // Call the method
//        authRepository.loginRequest(credentials)
//
//        // Verify no interaction with the DAO
//        coVerify(exactly = 0) { userDao.upsert(any()) }
//    }
//
//    @Test
//    fun `clearToken clears user data`() = runBlocking {
//        // Mock the DAO
//        coEvery { userDao.clear() } just Runs
//
//        // Call the method
//        authRepository.clearToken()
//
//        // Verify interactions
//        coVerify { userDao.clear() }
//    }
}