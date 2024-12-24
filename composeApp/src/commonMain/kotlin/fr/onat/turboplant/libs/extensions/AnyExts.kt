package fr.onat.turboplant.libs.extensions

fun Any?.toStringOrNull() = this?.toString() ?: this as String?