package fr.onat.turboplant.data.api

import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.logger.logger
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.mockative.Mockable
import kotlinx.coroutines.flow.first

@Mockable(ArchiApi::class, HttpResponse::class)
class ArchiApi(
    private val client: IHttpClient,
    private val userDao: UserDao,
) {
    suspend fun getToken() = userDao.getAll().first().first().token

    //    private val baseUrl = "http://90.40.139.106:3000"
    private val baseUrl = "http://127.0.0.1:3000"

    suspend fun get(
        routeUrl: String,
    ): HttpResponse? {
        val token = getToken()
        try {
            val url = "$baseUrl$routeUrl"
            return client.get {
                url {
                    url(url)
                }
                token?.let { headers.append("Authorization", "Bearer $it") }

            }
        } catch (e: Exception) {
            logger(e.message)
            return null
        }
    }

    suspend fun post(
        routeUrl: String,
        body: Any? = null,
        onError: () -> Unit = {}
    ): HttpResponse? {
        val token = getToken()
        try {
            val url = "$baseUrl$routeUrl"
            return client.post {
                url(url)
                contentType(ContentType.Application.Json)
                body?.let { setBody(it) }
                token?.let { headers.append("Authorization", "Bearer $it") }
            }
        } catch (e: Exception) {
            onError()
            logger(e.message)
            return null
        }
    }

    suspend fun put(
        routeUrl: String,
        body: Any? = null,
        onError: () -> Unit = {}
    ): HttpResponse? {
        val token = getToken()
        try {
            val url = "$baseUrl$routeUrl"
            return HttpClient().put {
                url(url)
                contentType(ContentType.Application.Json)
                body?.let { setBody(it) }
                token?.let { headers.append("Authorization", "Bearer $it") }
            }
        } catch (e: Exception) {
            onError()
            logger(e.message)
            return null
        }
    }
}

suspend inline fun <reified T> HttpResponse.bodyDebug(): T = body<T>().also { logger(bodyAsText()) }