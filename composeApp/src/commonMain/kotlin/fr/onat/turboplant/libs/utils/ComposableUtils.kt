package fr.onat.turboplant.libs.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.NavRoute.Companion.getRoute
import fr.onat.turboplant.presentation.navigationBar.NavigationBar
import fr.onat.turboplant.presentation.navigationBar.NavBarItem

@Composable
expect fun calculateWindowSizeClass(): WindowSizeClass

val LocalWindowWidthSizeClass = compositionLocalOf { WindowWidthSizeClass.Medium }
val LocalNavRoute = compositionLocalOf<NavRoute?> { null }

@Composable
fun setMaterialWithProviders(
    navController: NavController,
    vararg values: ProvidedValue<*>,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalWindowWidthSizeClass provides calculateWindowSizeClass().widthSizeClass,
            LocalNavRoute provides navController.getCurrentRoute().value, // todo not proper, find a way to use navBackStackEntry.toRoute
            LocalTextStyle provides TextStyle.Companion.Default.copy(color = Color.White),
            LocalContentColor provides Color.White,
            LocalContentAlpha provides 0.4f,
            *values
        ) {
            content()
        }
    }
}

@Composable
fun NavController.getCurrentRoute(): State<NavRoute?> {
    val navBackStackEntry by currentBackStackEntryAsState()
    return remember { derivedStateOf { navBackStackEntry?.destination?.getRoute() } }
}

@Composable
fun isCompactLayout() = LocalWindowWidthSizeClass.current <= WindowWidthSizeClass.Medium

@Composable
fun isExpendedLayout() = LocalWindowWidthSizeClass.current > WindowWidthSizeClass.Medium