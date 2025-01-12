package fr.onat.turboplant.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import fr.onat.turboplant.libs.extensions.getCurrentRoute
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.libs.utils.setMaterialWithProviders
import fr.onat.turboplant.presentation.composables.AlwaysDeniedDialog
import fr.onat.turboplant.presentation.auth.AuthScreen
import fr.onat.turboplant.presentation.navigationBar.NavBarItem
import fr.onat.turboplant.presentation.navigationBar.NavigationBar
import fr.onat.turboplant.presentation.permissions.PermissionsViewModel
import fr.onat.turboplant.presentation.plants.identification.PlantIdentificationScreen
import fr.onat.turboplant.presentation.plants.newPlant.NewPlantScreen
import fr.onat.turboplant.presentation.plants.plantList.PlantListScreen
import fr.onat.turboplant.presentation.rooms.RoomsScreen
import fr.onat.turboplant.presentation.tasks.TasksScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

val UnimplementedIcon = Icons.Default.Warning // todo implement icons

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)

    val permissionsViewModel = viewModel { PermissionsViewModel(controller) }
    val isCameraPermissionGranted = permissionsViewModel.handlePermission(Permission.CAMERA)

    if (!isCameraPermissionGranted)
        AlwaysDeniedDialog(
            onOpenSettings = { permissionsViewModel.openAppSettings() },
            onDismiss = {}
        )

    setMaterialWithProviders(
        LocalNavRoute provides navController.getCurrentRoute()
    ) {
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
                    AuthScreen(navigate = {
                        navController.navigate(PlantsRoute) {
                            navController.graph.startDestinationRoute?.let { startDestination ->
                                popUpTo(startDestination) {
                                    inclusive = true
                                }
                            }
                            launchSingleTop = true
                        }
                    })
                }
                composable<PlantsRoute> { PlantListScreen(navigate = { navController.navigate(it) }) }
                composable<AddNewPlantRoute> {
                    NewPlantScreen(navigate = { navController.navigate(it) })
                }
                composable<CameraRoute> {
                    PlantIdentificationScreen(Modifier.fillMaxSize())
                }
                composable<TasksRoute> { TasksScreen() }
                composable<RoomsRoute> { RoomsScreen() }

            }

            if (!NavBarItem.exemptedRoutes.contains(LocalNavRoute.current))
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
                AddNewPlantRoute::class.qualifiedName -> AddNewPlantRoute
                CameraRoute::class.qualifiedName -> CameraRoute
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
object AddNewPlantRoute : NavRoute

@Serializable
object TasksRoute : NavRoute

@Serializable
object RoomsRoute : NavRoute

@Serializable
object CameraRoute : NavRoute

@Serializable
object NotImplementedRoute : NavRoute
