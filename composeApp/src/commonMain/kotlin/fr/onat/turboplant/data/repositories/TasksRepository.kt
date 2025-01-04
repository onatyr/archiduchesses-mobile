package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.TaskDao
import kotlinx.coroutines.flow.map

class TasksRepository(
    private val archiApi: ArchiApi,
    private val taskDao: TaskDao
) {
    fun getAllNotDone() = taskDao.getAllNotDone().map { list -> list.sortedBy { it.task.dueDate } }

    suspend fun updateDone(id: String, done: Boolean) {
        taskDao.updateDone(id, done)
        archiApi.put("/tasks/complete/$id")
    }
}