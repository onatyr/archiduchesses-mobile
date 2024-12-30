package fr.onat.turboplant.logger

import platform.Foundation.NSLog

actual fun logger(vararg content: Any?) {
    NSLog(content.joinToString { it.toString() })
}