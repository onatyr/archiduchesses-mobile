package fr.onat.turboplant.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TasksViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {
    val tasks = taskRepository.getAll()

    fun updateDone(id: String, done: Boolean) =
        viewModelScope.launch(Dispatchers.IO) { taskRepository.updateDone(id, done) }
}