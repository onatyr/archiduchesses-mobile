package fr.onat.turboplant.libs.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal fun ViewModel.asyncLaunch(
    context: CoroutineContext = Dispatchers.IO,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, block = block)