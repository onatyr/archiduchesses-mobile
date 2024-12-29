package fr.onat.turboplant.libs.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray?.toImageBitmap() = try {
    if (this == null) ImageBitmap(1, 1)
    else BitmapFactory.decodeByteArray(this, 0, this.size).asImageBitmap()
} catch (e: Exception) {
    ImageBitmap(1, 1)
}