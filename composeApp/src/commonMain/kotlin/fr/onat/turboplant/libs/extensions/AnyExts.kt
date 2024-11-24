package fr.onat.turboplant.libs.extensions

fun Any?.isNull() = this == null

fun Any?.toStringOrNull() = if (this.isNull()) this else toString()