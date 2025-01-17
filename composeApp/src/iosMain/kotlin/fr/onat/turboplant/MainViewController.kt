package fr.onat.turboplant

import androidx.compose.ui.window.ComposeUIViewController
import fr.onat.turboplant.modules.initKoin
import fr.onat.turboplant.presentation.App

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}