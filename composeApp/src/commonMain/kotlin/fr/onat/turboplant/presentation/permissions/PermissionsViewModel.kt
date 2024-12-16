package fr.onat.turboplant.presentation.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch

class PermissionsViewModel(private val controller: PermissionsController) : ViewModel() {

    init {
        viewModelScope.launch {
            CurrentPermissions.entries.forEach {
                it.currentState = controller.getPermissionState(it.permission)
            }
        }
    }

    fun getAllPermissions() = CurrentPermissions.entries.map { it.permission }

    fun getPermissionState(permission: Permission) = permission.currentPermission?.currentState


    fun provideOrRequestPermission(permission: Permission) {
        viewModelScope.launch {
            try {
                controller.providePermission(permission)
                permission.currentPermission?.currentState = PermissionState.Granted

            } catch (e: DeniedAlwaysException) {
                permission.currentPermission?.currentState = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                permission.currentPermission?.currentState = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun openAppSettings() = controller.openAppSettings()

    private enum class CurrentPermissions(
        val permission: Permission,
        var currentState: PermissionState,
    ) {
        CAMERA(Permission.CAMERA, PermissionState.NotDetermined)
    }

    private val Permission.currentPermission: CurrentPermissions?
        get() = CurrentPermissions.entries.find { it.permission == this }
}


