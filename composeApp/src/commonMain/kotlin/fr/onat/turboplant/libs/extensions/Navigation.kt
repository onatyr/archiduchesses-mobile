package fr.onat.turboplant.libs.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import fr.onat.turboplant.libs.utils.LocalNavRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.NavRoute.Companion.getRoute
import fr.onat.turboplant.presentation.navigationBar.NavBarItem

@Composable
fun NavController.getCurrentRoute(): NavRoute? {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentRoute by remember { derivedStateOf { navBackStackEntry?.destination?.getRoute() } }
    return currentRoute
}