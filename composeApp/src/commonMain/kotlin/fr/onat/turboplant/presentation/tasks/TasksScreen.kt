package fr.onat.turboplant.presentation.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(viewModel: TasksViewModel = koinViewModel()) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    Column {
        tasks.forEach {
            Text(it.id)
            Text(it.dueDate)
        }
    }
}