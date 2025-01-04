package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Upsert
    suspend fun upsert(place: Room)

    @Upsert
    suspend fun upsertAll(place: List<Room>)

    @Query("DELETE FROM Room")
    suspend fun clear()

    @Query("SELECT * FROM Room")
    fun getAll(): Flow<List<Room>>
}