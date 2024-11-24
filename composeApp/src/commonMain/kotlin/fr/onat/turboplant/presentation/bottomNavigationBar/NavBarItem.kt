package fr.onat.turboplant.presentation.bottomNavigationBar

import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.NotImplementedRoute
import fr.onat.turboplant.presentation.PlantsRoute
import org.jetbrains.compose.resources.DrawableResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.check_icon
import turboplant.composeapp.generated.resources.home_icon
import turboplant.composeapp.generated.resources.plant_icon


sealed class NavBarItem(val label: String, val icon: DrawableResource, val route: NavRoute) {
    companion object {
        val values = listOf(Plants, Tasks, Rooms)
        val routes = listOf(Plants.route, Tasks.route, Rooms.route)
    }
    data object Plants: NavBarItem("Mes plantes", Res.drawable.plant_icon, PlantsRoute)
    data object Tasks: NavBarItem("Tâches", Res.drawable.check_icon, NotImplementedRoute)//TasksRoute)
    data object Rooms: NavBarItem("Lieux", Res.drawable.home_icon, NotImplementedRoute)//RoomsRoute)
}