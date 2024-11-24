package fr.onat.turboplant.logger

import platform.Foundation.NSLog

actual fun logger(content: Any?) {
    NSLog(content.toString())
}