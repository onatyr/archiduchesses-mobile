package fr.onat.turboplant.libs.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import fr.onat.turboplant.libs.extensions.getActivity

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
actual fun calculateWindowSizeClass(): WindowSizeClass =
    calculateWindowSizeClass(LocalContext.current.getActivity())

@Composable
actual fun getScreenSize(): ScreenSize = ScreenSize(
    widthDp = LocalConfiguration.current.screenWidthDp,
    heightDp = LocalConfiguration.current.screenHeightDp
)