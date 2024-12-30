package fr.onat.turboplant.libs.extensions

import kotlin.math.abs

fun Long.toStringWithUnit(singular: String, plural: String) =
    "$this ${if (abs(this) > 1) plural else singular}"