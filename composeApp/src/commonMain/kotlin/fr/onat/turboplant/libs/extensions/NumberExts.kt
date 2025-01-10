package fr.onat.turboplant.libs.extensions

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.error_negative_value
import kotlin.math.abs

fun Long.toStringWithUnit(singular: String, plural: String): String {
    require(this > 0) { Res.string.error_negative_value }
    return "$this ${if (abs(this) > 1) plural else singular}"
}

fun Float.convertPxToDp(density: Density) = with(density) { toDp() }

fun Dp.toPx(density: Density) = with(density) { toPx() }