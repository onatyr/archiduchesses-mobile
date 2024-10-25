package fr.onat.turboplant

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.viewModels.NavigationRoute
import fr.onat.turboplant.viewModels.NavigationViewModel
import fr.onat.turboplant.views.LoginScreen
import fr.onat.turboplant.views.PlantListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

val LocalNavigate = compositionLocalOf<(NavigationRoute) -> Unit> { {} }

@Composable
@Preview
fun App() {
    val navigationViewModel = NavigationViewModel()
    val screenDisplayed by navigationViewModel.screen.collectAsStateWithLifecycle()

    MaterialTheme {
        CompositionLocalProvider(
            LocalNavigate provides { route -> navigationViewModel.navigate(route) }
        ) {
            when (screenDisplayed) {
                NavigationRoute.LOGIN -> LoginScreen()
                NavigationRoute.PLANT_LIST -> PlantListScreen()
            }
        }
    }

}