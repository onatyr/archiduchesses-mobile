package fr.onat.turboplant.presentation.navigationBar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.presentation.NotImplementedRoute
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier = modifier.height(60.dp),
        backgroundColor = Colors.TurboGreen,
    ) {
        val currentRoute = LocalNavRoute.current

        NavBarItem.values.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (item.route !== NotImplementedRoute) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text(item.label) },
            )
        }
    }
}