package fr.onat.turboplant.logger

import android.util.Log

actual fun logger(content: Any?) {
    Log.e("ERROR", content.toString())
}