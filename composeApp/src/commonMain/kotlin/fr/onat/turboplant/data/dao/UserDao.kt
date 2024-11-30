package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(user: User)

    @Query("DELETE FROM User")
    suspend fun clear()

    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<User>>
}