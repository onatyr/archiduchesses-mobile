package fr.onat.turboplant.presentation.plantList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.composables.CameraView
import fr.onat.turboplant.resources.Colors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddNewPlantScreen(
    viewModel: PlantViewModel = koinViewModel(),
    navigate: (NavRoute) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
    logger(searchResult)
    BoxWithConstraints {
        Column {
            TextField(
                value = searchQuery,
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                })

            Text(searchResult)
        }

        ImageIdentificationButton()
    }
}

@Composable
fun BoxScope.ImageIdentificationButton() {
    Box {
        CameraView(Modifier.fillMaxSize())

        Button(
            onClick = {},
            modifier = Modifier.size(100.dp).align(Alignment.BottomCenter).padding(20.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Colors.SalmonPink,
                contentColor = Color.Black
            )
        ) {
            Icon(Icons.Default.Add, "add new plant", Modifier.scale(2f))
        }
    }

}