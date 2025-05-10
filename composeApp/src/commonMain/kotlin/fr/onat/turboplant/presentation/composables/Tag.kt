package fr.onat.turboplant.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.data.models.dto.TaskType
import fr.onat.turboplant.libs.extensions.onlyFirstCharUppercase
import fr.onat.turboplant.resources.Colors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.droplet_icon

@Composable
fun WateringTaskTag() {
    Tag(
        color = Colors.WateringTagBackground,
        iconRes = Res.drawable.droplet_icon,
        name = TaskType.WATERING.name.onlyFirstCharUppercase()
    )
}

@Composable
fun Tag(color: Color, iconRes: DrawableResource, name: String) {
    Row(
        Modifier
            .wrapContentSize()
            .background(color = color, shape = RoundedCornerShape(20))
            .padding(vertical = 5.dp, horizontal = 8.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = Color.White
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = name,
            color = Color.White
        )
    }
}