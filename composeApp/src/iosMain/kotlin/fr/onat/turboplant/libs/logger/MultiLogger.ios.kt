package fr.onat.turboplant.libs.logger

import platform.Foundation.NSLog

actual fun logger(vararg content: Any?) {
    NSLog(content.joinToString { it.toString() })
}