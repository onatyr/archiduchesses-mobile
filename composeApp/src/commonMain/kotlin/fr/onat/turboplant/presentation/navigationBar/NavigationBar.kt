package fr.onat.turboplant.presentation.navigationBar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.onat.turboplant.presentation.NotImplementedRoute
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    BottomNavigation(
        modifier = modifier.height(60.dp),
        backgroundColor = Colors.TurboGreen,
    ) {
        NavBarItem.values.forEach { item ->
            val isSelected = item.isSelected()

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (item.route == NotImplementedRoute || isSelected) return@BottomNavigationItem
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startDestination ->
                            popUpTo(startDestination) {
                                inclusive = true
                            }
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = stringResource(item.labelRes),
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text(stringResource(item.labelRes)) },
                selectedContentColor = Colors.SalmonPink,
                unselectedContentColor = LocalContentColor.current.copy(LocalContentAlpha.current)
            )
        }
    }
}