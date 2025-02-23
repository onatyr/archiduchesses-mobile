package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.UserDao
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
    fun `isAuthenticated returns true when a token is returned`() {
        every { userDao.getToken() }.invokes { flowOf("mockToken") }

        val isAuthenticated = authRepository.isAuthenticated()

        runBlocking {
            assertTrue(isAuthenticated.first())
        }
    }

    @Test
    fun `isAuthenticated returns false when no token is returned`() {
        every { userDao.getToken() }.invokes { flowOf(null) }

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
}