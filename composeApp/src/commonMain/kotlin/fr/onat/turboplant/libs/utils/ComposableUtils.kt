package fr.onat.turboplant.libs.utils

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import fr.onat.turboplant.presentation.NavRoute

data class ScreenSize(val widthDp: Int, val heightDp: Int)

val LocalScreenSize = compositionLocalOf { ScreenSize(0, 0) }
val LocalWindowWidthSizeClass = compositionLocalOf { WindowWidthSizeClass.Medium }
val LocalSnackbarHostState = compositionLocalOf { SnackbarHostState() }
val LocalNavRoute = compositionLocalOf<NavRoute?> { null }

@Composable
expect fun calculateWindowSizeClass(): WindowSizeClass

@Composable
expect fun getScreenSize(): ScreenSize

@Composable
fun setMaterialWithProviders(
    vararg values: ProvidedValue<*>,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        MaterialTheme {
            CompositionLocalProvider(
                LocalWindowWidthSizeClass provides calculateWindowSizeClass().widthSizeClass,
                LocalSnackbarHostState provides snackbarHostState,
                LocalScreenSize provides getScreenSize(),
                LocalTextStyle provides TextStyle.Companion.Default.copy(color = Color.White),
                LocalContentColor provides Color.White,
                LocalContentAlpha provides 0.4f,
                *values
            ) {
                content()
            }
        }
    }
}

@Composable
fun isCompactLayout() = LocalWindowWidthSizeClass.current <= WindowWidthSizeClass.Medium

@Composable
fun isExpendedLayout() = LocalWindowWidthSizeClass.current > WindowWidthSizeClass.Medium

@Composable
fun onDispose(block: () -> Unit) = DisposableEffect(Unit) { onDispose { block() } }