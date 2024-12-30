package fr.onat.turboplant.data.repositories

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.dao.TaskDao

class TaskRepository(
    private val archiApi: ArchiApi,
    private val taskDao: TaskDao
) {
    fun getAll() = taskDao.getAll()
}