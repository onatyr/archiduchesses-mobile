package fr.onat.turboplant.api

import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.models.Plant
import fr.onat.turboplant.modules.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers

class ArchiApi {
    private val client = httpClient
    private var token = ""

    private val baseUrl = "http://90.40.139.106:3000"
//    private val baseUrl = "http://127.0.0.1:3000"

    private suspend fun get(
        routeUrl: String,
    ): HttpResponse {
        val url = "$baseUrl$routeUrl"
        return client.get {
            url(url)
            headers {
                append("Authorization", "Bearer $token")
            }
        }
    }

    suspend fun fetchPlants(): List<Plant> {
        return get("/plant/all").body()
    }

    suspend fun loginRequest(credentials: Credentials) {
        try {
            val fullUrl = "$baseUrl/auth/login"
            val loginResponse = client.post {
                url(fullUrl)
                contentType(ContentType.Application.Json)
                setBody(credentials)
            }
            if (loginResponse.status != HttpStatusCode.OK) return
            token = loginResponse.body()
            logger(token)
        } catch (e: Exception) {
            logger(e.message)
        }
    }
}