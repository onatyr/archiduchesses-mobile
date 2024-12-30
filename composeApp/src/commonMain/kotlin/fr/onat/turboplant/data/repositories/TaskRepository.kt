package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.TaskDao
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val archiApi: ArchiApi,
    private val taskDao: TaskDao
) {
    fun getAll() = taskDao.getAll().map { list -> list.sortedBy { it.task.dueDate } }

    suspend fun updateDone(id: String, done: Boolean) = taskDao.updateDone(id, done)
}