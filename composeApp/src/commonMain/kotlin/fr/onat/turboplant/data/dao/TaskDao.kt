package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.Task
import fr.onat.turboplant.data.entities.TaskWithPlant
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertAll(tasks: List<Task>)

    @Upsert
    suspend fun upsert(task: Task)

    @Query("""
        SELECT * FROM Task
            WHERE done = false
            """)
    fun getAllNotDone(): Flow<List<TaskWithPlant>>

    @Query("""
        UPDATE Task
            SET done = :done
            WHERE id = :id
            """)
    suspend fun updateDone(id: String, done: Boolean)
}