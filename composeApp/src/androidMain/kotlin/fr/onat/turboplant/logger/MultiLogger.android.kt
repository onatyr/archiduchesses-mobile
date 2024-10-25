package fr.onat.turboplant.logger

import android.util.Log

actual fun logger(message: String?) {
    Log.e("ERROR", message ?:"null")
}