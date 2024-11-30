package fr.onat.turboplant.data.api

import fr.onat.turboplant.data.database.AppDatabase
import fr.onat.turboplant.logger.logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class ArchiApi(
    private val client: HttpClient,
    private val database: AppDatabase,
) {
    private var token: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            database.getUserDao().getAll().collect {
                if (it.isEmpty()) return@collect
                token = it.first().token
            }
        }
    }

    //    private val baseUrl = "http://90.40.139.106:3000"
    private val baseUrl = "http://127.0.0.1:3000"

    suspend fun get(
        routeUrl: String,
        vararg queryParams: Parameters
    ): HttpResponse? {
        try {
            val url = "$baseUrl$routeUrl"
            return client.get {
                url {
                    url(url)
                    queryParams.forEach { param ->
                        parameters.appendAll(param)
                    }
                }
                headers.append("Authorization", "Bearer $token")

            }
        } catch (e: Exception) {
            logger(e.message)
            return null
        }
    }

    suspend fun post(
        routeUrl: String,
        body: Any,
        onError: () -> Unit = {}
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
}