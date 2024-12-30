package fr.onat.turboplant.libs.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import platform.UIKit.UIScreen

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
actual fun calculateWindowSizeClass() = calculateWindowSizeClass()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenSize(): ScreenSize = ScreenSize(
    widthDp = LocalWindowInfo.current.containerSize.width.pxToPoint(),
    heightDp = LocalWindowInfo.current.containerSize.height.pxToPoint()
)

fun Int.pxToPoint() = (this / UIScreen.mainScreen.scale).toInt()