package fr.onat.turboplant.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NavigationRoute {
    LOGIN,
    PLANT_LIST
}

class NavigationViewModel: ViewModel() {

    private val _screen = MutableStateFlow(NavigationRoute.LOGIN)
    val screen = _screen.asStateFlow()

    fun navigate(route: NavigationRoute) = _screen.update { route }
}