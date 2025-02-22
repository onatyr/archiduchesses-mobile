package fr.onat.turboplant.data

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.api.DefaultHttpClient
import fr.onat.turboplant.data.dao.UserDao
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json

class ArchiApiMock(responseContent: String, userDao: UserDao) : ArchiApi(
    client = DefaultHttpClient(HttpClient(getMockEngine(responseContent)) {
        install(ContentNegotiation)
        {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15_000
        }
    }),
    userDao = userDao
) {
    companion object {
        fun getMockEngine(responseContent: String) = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseContent),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }
}




