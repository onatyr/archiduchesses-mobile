package fr.onat.turboplant.presentation.navigationBar

import androidx.compose.runtime.Composable
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.presentation.AddNewPlantRoute
import fr.onat.turboplant.presentation.CameraRoute
import fr.onat.turboplant.presentation.LoginRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.PlantsRoute
import fr.onat.turboplant.presentation.ProfileRoute
import fr.onat.turboplant.presentation.RoomsRoute
import fr.onat.turboplant.presentation.TasksRoute
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.check_icon
import turboplant.composeapp.generated.resources.eye_scan_icon
import turboplant.composeapp.generated.resources.home_icon
import turboplant.composeapp.generated.resources.nav_route_places
import turboplant.composeapp.generated.resources.nav_route_plants
import turboplant.composeapp.generated.resources.nav_route_profile
import turboplant.composeapp.generated.resources.nav_route_tasks
import turboplant.composeapp.generated.resources.plant_icon


sealed class NavBarItem(
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val route: NavRoute,
    internal val relatedRoutes: List<NavRoute> = emptyList()
) {
    companion object {
        val values = listOf(Plants, Tasks, Rooms, Profile)
        val exemptedRoutes = listOf(LoginRoute)
    }

    data object Plants : NavBarItem(
        labelRes = Res.string.nav_route_plants,
        iconRes = Res.drawable.plant_icon,
        route = PlantsRoute,
        relatedRoutes = listOf(AddNewPlantRoute, CameraRoute)
    )

    data object Tasks : NavBarItem(
        labelRes = Res.string.nav_route_tasks,
        iconRes = Res.drawable.check_icon,
        route = TasksRoute
    )

    data object Rooms : NavBarItem(
        labelRes = Res.string.nav_route_places,
        iconRes = Res.drawable.home_icon,
        route = RoomsRoute
    )

    data object Profile : NavBarItem(
        labelRes = Res.string.nav_route_profile,
        iconRes = Res.drawable.eye_scan_icon,
        route = ProfileRoute
    )
}

@Composable
fun NavBarItem.isSelected() =
    route == LocalNavRoute.current || relatedRoutes.contains(LocalNavRoute.current)