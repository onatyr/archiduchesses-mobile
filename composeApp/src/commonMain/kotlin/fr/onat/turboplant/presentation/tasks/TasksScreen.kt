package fr.onat.turboplant.presentation.tasks

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = koinViewModel()) {

    val tasksWithPlant by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    LazyColumn {
        items(tasksWithPlant) {
            TaskCard(it)
            Spacer(Modifier.size(10.dp))
        }
    }
}