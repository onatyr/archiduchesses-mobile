package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.dto.UserDto
import fr.onat.turboplant.data.entities.User
import fr.onat.turboplant.data.entities.toUser
import io.mockative.every
import io.mockative.mock
import io.mockative.of
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthRepositoryTest {

    private val archiApi = mock(of<ArchiApi>())
    private val userDao = mock(of<UserDao>())
    private val authRepository = AuthRepository(archiApi, userDao)

    @BeforeTest
    fun setup() {
        every { userDao.getAll() } returns flowOf(listOf(User(id= "mockId", token = "mockToken")))
    }

    @Test
    fun `isAuthenticated returns true when user has a token`() {
        val isAuthenticated = authRepository.isAuthenticated()
        runBlocking {
            assertTrue(isAuthenticated.first())
        }
    }

//    @BeforeTest
//    fun setup() {
//        archiApi =
//            userDao = mockk()
//        authRepository = AuthRepository(archiApi, userDao)
//    }
//
//    @Test
//    fun `isAuthenticated returns true when user has a token`() {
//        every { userDao.getAll() } returns flowOf(listOf(UserDto(token = "token123").toUser()))
//
//        val isAuthenticated = authRepository.isAuthenticated()
//        runBlocking {
//            assertTrue(isAuthenticated.first())
//        }
//    }
//
//    @Test
//    fun `isAuthenticated returns false when no users or token is null`() {
//        // Mock the DAO to return a flow with no users
//        every { userDao.getAll() } returns flowOf(emptyList())
//
//        // Assert the result
//        val isAuthenticated = authRepository.isAuthenticated()
//        runBlocking {
//            assertFalse(isAuthenticated.first())
//        }
//    }
//
//    @Test
//    fun `loginRequest successfully logs in user`() = runBlocking {
//        val credentials = Credentials(username = "test", password = "password")
//        val userDto = UserDto(id = 1, token = "token123")
//
//        // Mock the API response
//        val response = mockk<HttpResponse> {
//            every { status } returns HttpStatusCode.OK
//            coEvery { body<UserDto>() } returns userDto
//        }
//        coEvery { archiApi.post("/auth/login", credentials) } returns response
//
//        // Mock the DAO
//        coEvery { userDao.upsert(any()) } just Runs
//
//        // Call the method
//        authRepository.loginRequest(credentials)
//
//        // Verify interactions
//        coVerify { userDao.upsert(userDto.toUser()) }
//    }
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