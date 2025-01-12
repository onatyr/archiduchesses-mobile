package fr.onat.turboplant.libs.extensions

import fr.onat.turboplant.logger.logger
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

suspend inline fun <reified T> HttpResponse.bodyDebug(): T = body<T>().also { logger(bodyAsText()) }

fun HttpResponse?.onSuccess(block: (HttpResponse) -> Unit): HttpResponse? {
    if (isSuccessful()) block(this)
    return this
}

fun HttpResponse?.onFailure(block: (HttpResponse?) -> Unit): HttpResponse? {
    if (!isSuccessful()) block(this)
    return this
}

@OptIn(ExperimentalContracts::class)
fun HttpResponse?.isSuccessful(): Boolean {
    contract {
        returns(true) implies (this@isSuccessful != null)
    }
    return this != null && (this.status == HttpStatusCode.OK || this.status == HttpStatusCode.Created)
}
