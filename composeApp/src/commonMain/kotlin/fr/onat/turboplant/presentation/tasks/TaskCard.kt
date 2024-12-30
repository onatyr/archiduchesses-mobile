package fr.onat.turboplant.presentation.tasks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.data.entities.TaskWithPlant
import fr.onat.turboplant.libs.extensions.convertPxToDp
import fr.onat.turboplant.libs.extensions.toPx
import fr.onat.turboplant.libs.extensions.toStringWithUnit
import fr.onat.turboplant.libs.utils.LocalScreenSize
import fr.onat.turboplant.logger.logger
import fr.onat.turboplant.presentation.UnimplementedIcon
import fr.onat.turboplant.resources.Colors
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun TaskCard(taskWithPlant: TaskWithPlant, updateDone: (String, Boolean) -> Unit) {
    val (task, plant) = taskWithPlant

    val screenWidth = LocalScreenSize.current.widthDp
    val density = LocalDensity.current

    var targetOffset by remember { mutableStateOf(0f) }
    val animatedOffset by animateFloatAsState(targetValue = targetOffset, finishedListener = {
        if (targetOffset == screenWidth.dp.toPx(density)) updateDone(task.id, true)
    })

    Card(elevation = 20.dp, modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Colors.PlantCardGreen)
                .padding(horizontal = 10.dp)
                .offset { IntOffset(animatedOffset.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount -> targetOffset += if (dragAmount > 0f) dragAmount else 0f },
                        onDragEnd = {
                            targetOffset =
                                if (targetOffset.convertPxToDp(density) > (screenWidth - screenWidth / 2.5).dp)
                                    screenWidth.dp.toPx(density)
                                else 0f
                        }
                    )
                },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(UnimplementedIcon, null)
                plant?.let { Text(it.name) }
            }
            Text(getDisplayableDayCount(task.dueDate))
        }
    }
}

fun getDisplayableDayCount(dueDate: Instant): String {
    val dayCountString: (Long) -> String =
        {
            if (it > 0) "in " else "" +
                    abs(it).toStringWithUnit("day", "days") +
                    if (it < 0) " ago" else ""
        }

    val timeDeltaDays = (dueDate - Clock.System.now()).inWholeDays
    return when {
        timeDeltaDays < -1 -> dayCountString(timeDeltaDays)
        timeDeltaDays > 1 -> dayCountString(timeDeltaDays)
        else -> "today"
    }
}