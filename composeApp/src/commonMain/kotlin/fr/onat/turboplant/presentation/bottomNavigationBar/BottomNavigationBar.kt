package fr.onat.turboplant.presentation.bottomNavigationBar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.libs.utils.getCurrentRoute
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.NotImplementedRoute
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation(modifier.height(60.dp)) {
        val currentRoute = LocalNavRoute.current

        BottomSheetItem.values.forEach { item ->
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