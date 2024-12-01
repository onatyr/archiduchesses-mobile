package fr.onat.turboplant.presentation.navigationBar

import fr.onat.turboplant.presentation.AddNewPlantRoute
import fr.onat.turboplant.presentation.LoginRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.NotImplementedRoute
import fr.onat.turboplant.presentation.PlantsRoute
import fr.onat.turboplant.presentation.RoomsRoute
import fr.onat.turboplant.presentation.TasksRoute
import org.jetbrains.compose.resources.DrawableResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.check_icon
import turboplant.composeapp.generated.resources.home_icon
import turboplant.composeapp.generated.resources.plant_icon


sealed class NavBarItem(
    val label: String,
    val icon: DrawableResource,
    val route: NavRoute,
    val relatedRoutes: List<NavRoute>
) {
    companion object {
        val values = listOf(Plants, Tasks, Rooms)
        val exemptedRoutes = listOf(LoginRoute)
    }

    data object Plants : NavBarItem("Mes plantes", Res.drawable.plant_icon, PlantsRoute, listOf(AddNewPlantRoute))
    data object Tasks :
        NavBarItem("Tâches", Res.drawable.check_icon, TasksRoute, emptyList())

    data object Rooms :
        NavBarItem("Lieux", Res.drawable.home_icon, RoomsRoute, emptyList())
}