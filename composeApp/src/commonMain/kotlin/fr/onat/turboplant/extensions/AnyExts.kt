package fr.onat.turboplant.extensions

fun Any?.isNull() = this == null

fun Any?.toStringOrNull() = if (this.isNull()) this else toString()