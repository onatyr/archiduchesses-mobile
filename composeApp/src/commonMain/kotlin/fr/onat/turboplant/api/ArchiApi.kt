package fr.onat.turboplant.api

import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.models.Credentials
import fr.onat.turboplant.models.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class ArchiApi(private val client: HttpClient) {
    private var token: String? = null

    private val baseUrl = "http://90.40.139.106:3000"
//    private val baseUrl = "http://127.0.0.1:3000"

    suspend fun get(
        routeUrl: String,
    ): HttpResponse {
        val url = "$baseUrl$routeUrl"
        return client.get {
            url(url)
            headers.append("Authorization", "Bearer $token")
        }
    }

    suspend fun post(
        routeUrl: String,
        body: Any,
        onError: () -> Unit
    ): HttpResponse? {
        try {
            val url = "$baseUrl$routeUrl"
            return client.post {
                url(url)
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        } catch (e: Exception) {
            onError()
            logger(e.message)
            return null
        }
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun clearToken() {
        this.token = null
    }
}