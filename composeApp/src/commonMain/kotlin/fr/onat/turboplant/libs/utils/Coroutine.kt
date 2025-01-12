package fr.onat.turboplant.libs.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun asyncLaunch(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit
) = CoroutineScope(context).launch(block = block)