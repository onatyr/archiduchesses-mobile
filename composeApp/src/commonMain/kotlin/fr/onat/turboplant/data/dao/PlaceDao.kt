package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Upsert
    suspend fun upsert(place: Place)

    @Upsert
    suspend fun upsertAll(place: List<Place>)

    @Query("DELETE FROM Place")
    suspend fun clear()

    @Query("SELECT * FROM Place")
    fun getAll(): Flow<List<Place>>
}