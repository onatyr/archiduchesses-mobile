package fr.onat.turboplant.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.onat.turboplant.data.entities.TaskWithPlant
import fr.onat.turboplant.libs.extensions.toStringWithUnit
import fr.onat.turboplant.presentation.UnimplementedIcon
import fr.onat.turboplant.resources.Colors
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.abs

@Composable
fun TaskCard(taskWithPlant: TaskWithPlant) {
    val (task, plant) = taskWithPlant
    Card(elevation = 20.dp) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().background(Colors.PlantCardGreen)
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
        { "${if (it > 0) "in " else "" }${abs(it).toStringWithUnit("day", "days")}${if (it < 0) " ago" else "" }" }

    val timeDeltaDays = (dueDate - Clock.System.now()).inWholeDays
    return when {
        timeDeltaDays < -1 -> dayCountString(timeDeltaDays)
        timeDeltaDays > 1 -> dayCountString(timeDeltaDays)
        else -> "today"
    }
}