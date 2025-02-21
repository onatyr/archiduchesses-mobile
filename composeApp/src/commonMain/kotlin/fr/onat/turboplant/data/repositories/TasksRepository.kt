package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.TaskDao
import fr.onat.turboplant.libs.extensions.onSuccess
import fr.onat.turboplant.libs.extensions.onSuccessAsync
import fr.onat.turboplant.libs.utils.asyncLaunch
import kotlinx.coroutines.flow.map

class TasksRepository(
    private val archiApi: ArchiApi,
    private val taskDao: TaskDao
) {
    fun getAllNotDone() = taskDao.getAllNotDone().map { list -> list.sortedBy { it.task.dueDate } }

    suspend fun updateDone(id: String, done: Boolean) {
        archiApi.put("/tasks/complete/$id").onSuccessAsync {
            taskDao.updateDone(id, done)
        }
    }
}