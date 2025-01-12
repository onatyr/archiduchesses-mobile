package fr.onat.turboplant.libs.extensions

import fr.onat.turboplant.libs.utils.asyncLaunch
import fr.onat.turboplant.logger.logger
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract


fun HttpResponse?.debug(): HttpResponse? {
    this ?: return this
    asyncLaunch { logger("[HttpResponse] status: $status body: ${bodyAsText()}") }
    return this
}

fun HttpResponse?.onSuccess(block: (HttpResponse) -> Unit): HttpResponse? {
    if (isSuccessful()) {
        asyncLaunch { }
        block(this)
    }
    return this
}

fun HttpResponse?.onFailure(block: (HttpResponse?) -> Unit): HttpResponse? {
    if (!isSuccessful()) {
        block(this)
    }
    return this
}

@OptIn(ExperimentalContracts::class)
fun HttpResponse?.isSuccessful(): Boolean {
    contract {
        returns(true) implies (this@isSuccessful != null)
    }
    return this != null && (this.status == HttpStatusCode.OK || this.status == HttpStatusCode.Created)
}

@Serializable
data class HttpMessage(val message: String)

suspend fun HttpResponse?.getMessage(): String = try {
    this?.body<HttpMessage?>()?.message ?: ""
} catch (e: Exception) {
    ""
}