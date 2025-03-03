package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.resources.Colors

@Composable
private fun NewPlantFooterButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(80.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = color, disabledBackgroundColor = color)
    ) {
        Text(text = text)
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit
) {
    NewPlantFooterButton(
        onClick = onClick,
        color = Colors.SmoothGrey,
        text = "CANCEL"
    )
}

@Composable
fun ConfirmButton(
    onClick: () -> Unit,
    onDisabledClick: () -> Unit,
    enabled: Boolean
) {
    NewPlantFooterButton(
        onClick = if (enabled) onClick else onDisabledClick,
        color = if (enabled) Colors.TurboGreen else Colors.SmootherGrey.copy(alpha = 0.4f),
        text = "CONFIRM"
    )
}