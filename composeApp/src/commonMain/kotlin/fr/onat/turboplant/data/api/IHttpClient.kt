package fr.onat.turboplant.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse

interface IHttpClient {
    suspend fun get(block: HttpRequestBuilder.() -> Unit): HttpResponse
    suspend fun post(block: HttpRequestBuilder.() -> Unit): HttpResponse
    suspend fun put(block: HttpRequestBuilder.() -> Unit): HttpResponse
}

class DefaultHttpClient(private val client: HttpClient) : IHttpClient {
    override suspend fun get(block: HttpRequestBuilder.() -> Unit) = client.get(block)
    override suspend fun post(block: HttpRequestBuilder.() -> Unit) = client.post(block)
    override suspend fun put(block: HttpRequestBuilder.() -> Unit) = client.put(block)
}