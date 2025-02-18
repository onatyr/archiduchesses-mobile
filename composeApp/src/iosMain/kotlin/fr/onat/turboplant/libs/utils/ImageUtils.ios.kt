package fr.onat.turboplant.libs.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray?.toImageBitmap() =
    if (this == null) ImageBitmap(1, 1) else Image.makeFromEncoded(this).toComposeImageBitmap()