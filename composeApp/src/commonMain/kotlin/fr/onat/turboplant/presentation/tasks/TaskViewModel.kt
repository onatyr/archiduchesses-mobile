package fr.onat.turboplant.presentation.tasks

import androidx.lifecycle.ViewModel
import fr.onat.turboplant.data.repositories.TaskRepository

class TasksViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {
    val tasks = taskRepository.getAll()
}