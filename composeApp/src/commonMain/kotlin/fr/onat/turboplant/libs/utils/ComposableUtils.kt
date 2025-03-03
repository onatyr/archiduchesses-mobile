package fr.onat.turboplant.libs.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import fr.onat.turboplant.presentation.AuthRoute
import fr.onat.turboplant.presentation.NavRoute.Companion.name
import fr.onat.turboplant.presentation.navigationBar.NavigationBar

data class ScreenSize(val widthDp: Int, val heightDp: Int)

val LocalScreenSize = compositionLocalOf { ScreenSize(0, 0) }
val LocalWindowWidthSizeClass = compositionLocalOf { WindowWidthSizeClass.Medium }
val LocalSnackbarHostState = compositionLocalOf { SnackbarHostState() }
val LocalNavRoute = compositionLocalOf<String?> { null }

@Composable
expect fun calculateWindowSizeClass(): WindowSizeClass

@Composable
expect fun getScreenSize(): ScreenSize

@Composable
fun setMaterialWithProviders(
    navController: NavController,
    vararg values: ProvidedValue<*>,
    content: @Composable (Modifier) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
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
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = LocalSnackbarHostState.current)
                },
                bottomBar = {
                    if (LocalNavRoute.current != AuthRoute.name)
                        NavigationBar(navController, Modifier.fillMaxWidth())
                }
            ) { paddingValues ->
                content(Modifier.padding(bottom = paddingValues.calculateBottomPadding()))
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