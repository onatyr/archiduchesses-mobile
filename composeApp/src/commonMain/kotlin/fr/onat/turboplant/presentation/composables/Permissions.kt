package fr.onat.turboplant.presentation.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import fr.onat.turboplant.presentation.permissions.PermissionsViewModel

@Composable
fun handlePermission(permissionsViewModel: PermissionsViewModel, permission: Permission) {
    when (permissionsViewModel.getPermissionState(permission)) {
        PermissionState.Granted -> return
        PermissionState.DeniedAlways -> {
            AlwaysDeniedDialog(
                onOpenSettings = { permissionsViewModel.openAppSettings() },
                onDismiss = {}
            )
        }
        else -> {
            permissionsViewModel.provideOrRequestPermission(permission)
        }
    }
}

@Composable
fun AlwaysDeniedDialog(
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onOpenSettings()
                onDismiss()

            }) {
                Text("Open app settings")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(text = "Camera permission required") },
        text = { Text("We need camera permission in order to use the camera") }
    )

}