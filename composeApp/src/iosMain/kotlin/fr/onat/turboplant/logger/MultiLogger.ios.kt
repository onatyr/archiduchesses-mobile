package fr.onat.turboplant.logger

import platform.Foundation.NSLog

actual fun logger(message: String?) {
    NSLog(message ?:"")
}