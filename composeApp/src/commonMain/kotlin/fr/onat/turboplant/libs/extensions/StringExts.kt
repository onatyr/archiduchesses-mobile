package fr.onat.turboplant.libs.extensions

fun String.onlyFirstCharUppercase() =
    if (isEmpty()) this else get(0).uppercase() + substring(1).lowercase()