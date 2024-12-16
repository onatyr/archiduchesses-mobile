package fr.onat.turboplant.libs.utils

import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import fr.onat.turboplant.presentation.NavRoute

@Composable
expect fun calculateWindowSizeClass(): WindowSizeClass

val LocalWindowWidthSizeClass = compositionLocalOf { WindowWidthSizeClass.Medium }
val LocalNavRoute = compositionLocalOf<NavRoute?> { null }

@Composable
fun setMaterialWithProviders(
    vararg values: ProvidedValue<*>,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        CompositionLocalProvider(
            LocalWindowWidthSizeClass provides calculateWindowSizeClass().widthSizeClass,
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
fun isCompactLayout() = LocalWindowWidthSizeClass.current <= WindowWidthSizeClass.Medium

@Composable
fun isExpendedLayout() = LocalWindowWidthSizeClass.current > WindowWidthSizeClass.Medium