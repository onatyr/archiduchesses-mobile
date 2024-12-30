package fr.onat.turboplant.logger

import android.util.Log

actual fun logger(vararg content: Any?) {
    Log.e("LOGGER", content.joinToString { it.toString() })
}
