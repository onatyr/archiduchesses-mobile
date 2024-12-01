package fr.onat.turboplant.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.libs.utils.setMaterialWithProviders
import fr.onat.turboplant.presentation.login.LoginScreen
import fr.onat.turboplant.presentation.navigationBar.NavBarItem
import fr.onat.turboplant.presentation.navigationBar.NavigationBar
import fr.onat.turboplant.presentation.plantList.PlantListScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    setMaterialWithProviders(navController) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NavHost(
                navController = navController,
                startDestination = LoginRoute,
                modifier = Modifier.weight(1f)
            ) {
                composable<LoginRoute> {
                    LoginScreen(navigate = {
                        navController.navigate(PlantsRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    })
                }
                composable<PlantsRoute> { PlantListScreen() }
            }

            if (NavBarItem.routes.contains(LocalNavRoute.current))
                NavigationBar(navController, Modifier.fillMaxWidth())
        }
    }
}

interface NavRoute {
    companion object {
        fun NavDestination.getRoute(): NavRoute? {
            return when (route) {
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
object NotImplementedRoute : NavRoute
