package fr.onat.turboplant.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.onat.turboplant.data.repositories.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class TasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    val tasks = tasksRepository.getAllNotDone()

    fun updateDone(id: String, done: Boolean) =
        viewModelScope.launch(Dispatchers.IO) { tasksRepository.updateDone(id, done) }
}