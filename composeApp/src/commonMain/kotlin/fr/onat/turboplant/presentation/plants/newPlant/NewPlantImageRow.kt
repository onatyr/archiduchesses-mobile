package fr.onat.turboplant.presentation.plants.newPlant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.presentation.ImageIdentificationRoute
import fr.onat.turboplant.presentation.ImagePickerRoute
import fr.onat.turboplant.presentation.NavRoute
import fr.onat.turboplant.presentation.composables.SmoothGreyBox
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.add_image_icon
import turboplant.composeapp.generated.resources.eye_scan_icon

@Composable
fun ImageSelectorsRow(navigate: (NavRoute) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageSelector(
            openImageSelector = { navigate(ImagePickerRoute) },
            iconRes = Res.drawable.add_image_icon,
            subText = "Add a picture"
        )
        Spacer(Modifier.size(50.dp))
        ImageSelector(
            openImageSelector = { navigate(ImageIdentificationRoute) },
            iconRes = Res.drawable.eye_scan_icon,
            subText = "Identify a plant"
        )
    }
}

@Composable
fun ImageSelector(
    openImageSelector: () -> Unit,
    subText: String,
    iconRes: DrawableResource
) {
    SmoothGreyBox(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(
                bounded = true,
                color = Color.White
            ),
            onClick = openImageSelector
        )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        Text(subText)
    }
}