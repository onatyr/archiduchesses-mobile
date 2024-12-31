package fr.onat.turboplant.presentation.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.onat.turboplant.libs.utils.onDispose
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = koinViewModel()) {

    val tasksWithPlant by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    LazyColumn(Modifier.fillMaxSize()) {
        items(tasksWithPlant, key = { it.task.id }) {
            var visible by remember { mutableStateOf(true) }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = shrinkVertically()
            ) {
                TaskCard(
                    taskWithPlant = it,
                    onDone = { visible = false }
                )
                onDispose { viewModel.updateDone(it.task.id, true) }
            }
        }
    }
}