package fr.onat.turboplant.libs.extensions

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.abs

fun Long.toStringWithUnit(singular: String, plural: String) =
    "$this ${if (abs(this) > 1) plural else singular}"

fun Float.convertPxToDp(density: Density) = with(density) { toDp() }

fun Dp.toPx(density: Density) = with(density) { toPx() }