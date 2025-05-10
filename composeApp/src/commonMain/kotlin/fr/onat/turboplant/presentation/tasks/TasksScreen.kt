package fr.onat.turboplant.presentation.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.data.models.entities.TaskWithPlant
import fr.onat.turboplant.libs.extensions.isInNextDays
import fr.onat.turboplant.libs.extensions.isPast
import fr.onat.turboplant.libs.extensions.isToday
import fr.onat.turboplant.libs.logger.logger
import fr.onat.turboplant.libs.utils.onDispose
import fr.onat.turboplant.resources.Colors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = koinViewModel()) {

    val tasksWithPlant by viewModel.tasks.collectAsStateWithLifecycle(emptyList())

    val pastTasks = tasksWithPlant.filter { it.task.dueDate.isPast() }.ifEmpty { null }
    val todayTasks = tasksWithPlant.filter { it.task.dueDate.isToday() }.ifEmpty { null }
    val nextDaysTasks = tasksWithPlant
        .filter { it.task.dueDate.isInNextDays(7) && !it.task.dueDate.isToday() }
        .ifEmpty { null }

    logger("past:", pastTasks)
    logger("today", todayTasks)
    logger("next", nextDaysTasks)

    LazyColumn(Modifier.fillMaxSize()) {
        pastTasks?.let {
            taskListWithHeader(
                headerLabel = "Overdue",
                taskList = pastTasks,
                updateDone = viewModel::updateDone
            )
        }

        taskListWithHeader(
            headerLabel = "Today",
            taskList = todayTasks,
            updateDone = viewModel::updateDone
        )

        nextDaysTasks?.let {
            taskListWithHeader(
                headerLabel = "Next 7 days",
                taskList = nextDaysTasks,
                updateDone = viewModel::updateDone
            )
        }
    }
}

fun LazyListScope.taskListWithHeader(
    headerLabel: String,
    taskList: List<TaskWithPlant>?,
    updateDone: (String, Boolean) -> Unit
) {
    item {
        Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 10.dp)) {
            Text(text = headerLabel, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
    if (taskList != null)
        items(taskList, key = { it.task.id }) {
            var isDone by remember { mutableStateOf(false) }
            AnimatedVisibility(
                visible = !isDone,
                enter = fadeIn(),
                exit = shrinkVertically(),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                TaskCard(
                    taskWithPlant = it,
                    onDone = { isDone = true }
                )
                onDispose { if (isDone) updateDone(it.task.id, true) }
            }
        }
    else
        item {
            Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 5.dp)) {
                Text(
                    text = "Nothing to do !",
                    fontSize = 24.sp,
                    color = Colors.SmootherGrey,
                    fontStyle = FontStyle.Italic
                )
            }
            Spacer(Modifier.height(10.dp))
        }
}