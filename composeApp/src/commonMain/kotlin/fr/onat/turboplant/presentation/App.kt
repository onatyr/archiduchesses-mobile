package fr.onat.turboplant.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.onat.turboplant.libs.utils.setMaterialWithProviders
import fr.onat.turboplant.presentation.views.LoginScreen
import fr.onat.turboplant.presentation.views.PlantListScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    setMaterialWithProviders(navController) { modifier ->

        NavHost(navController = navController, startDestination = LoginRoute, modifier = modifier) {
            composable<LoginRoute> { LoginScreen(navigate = { navController.navigate(PlantsRoute) }) }
            composable<PlantsRoute> { PlantListScreen() }
        }
    }
}

interface NavRoute {
    companion object {
        fun NavDestination.getRoute(): NavRoute? {
            return when(this.route) {
                LoginRoute::class.qualifiedName -> LoginRoute
                PlantsRoute::class.qualifiedName -> PlantsRoute
                TasksRoute::class.qualifiedName -> TasksRoute
                RoomsRoute::class.qualifiedName -> RoomsRoute
                else -> null
            }
        }
    }
}

@Serializable
object LoginRoute : NavRoute

@Serializable
object PlantsRoute : NavRoute

@Serializable
object TasksRoute : NavRoute

@Serializable
object RoomsRoute : NavRoute

@Serializable
object NotImplementedRoute: NavRoute
