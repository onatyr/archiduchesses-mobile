package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.User
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(user: User)

    @Query("DELETE FROM User")
    suspend fun clear()

    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<User>>

    @Query("SELECT token FROM USER LIMIT 1")
    fun getToken(): Flow<String?>
}