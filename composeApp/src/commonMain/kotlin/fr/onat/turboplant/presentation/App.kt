package fr.onat.turboplant.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import fr.onat.turboplant.libs.extensions.getCurrentRoute
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.libs.utils.setMaterialWithProviders
import fr.onat.turboplant.presentation.auth.AuthScreen
import fr.onat.turboplant.presentation.composables.AlwaysDeniedDialog
import fr.onat.turboplant.presentation.permissions.PermissionsViewModel
import fr.onat.turboplant.presentation.plants.identification.PlantIdentificationScreen
import fr.onat.turboplant.presentation.plants.newPlant.NewPlantScreen
import fr.onat.turboplant.presentation.plants.plantDetailed.PlantDetailedScreen
import fr.onat.turboplant.presentation.plants.plantList.PlantListScreen
import fr.onat.turboplant.presentation.rooms.RoomsScreen
import fr.onat.turboplant.presentation.tasks.TasksScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        navController = navController,
        LocalNavRoute provides navController.getCurrentRoute()
    ) { modifier ->
        Column(
            modifier = modifier.fillMaxSize().background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            NavHost(
                navController = navController,
                startDestination = AuthRoute,
                modifier = Modifier.weight(1f)
            ) {
                composable<AuthRoute> {
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
                composable<PlantDetailedRoute> {
                    val args = it.toRoute<PlantDetailedRoute>()
                    PlantDetailedScreen(plantId = args.plantId)
                }
                composable<AddNewPlantRoute> {
                    NewPlantScreen(navigate = { navController.navigate(it) })
                }
                composable<CameraRoute> {
                    PlantIdentificationScreen(Modifier.fillMaxSize())
                }
                composable<TasksRoute> { TasksScreen() }
                composable<RoomsRoute> { RoomsScreen() }

            }
        }
    }
}

interface NavRoute {
    companion object {
        val NavRoute.name: String?
            get() = this::class.qualifiedName
    }
}

@Serializable
object AuthRoute : NavRoute

@Serializable
object PlantsRoute : NavRoute

@Serializable
data class PlantDetailedRoute(val plantId: String) : NavRoute

@Serializable
object AddNewPlantRoute : NavRoute

@Serializable
object TasksRoute : NavRoute

@Serializable
object RoomsRoute : NavRoute

@Serializable
object CameraRoute : NavRoute

@Serializable
object ProfileRoute : NavRoute

@Serializable
object NotImplementedRoute : NavRoute
