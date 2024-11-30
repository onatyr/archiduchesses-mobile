package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.entities.Plant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Upsert
    suspend fun upsert(plant: Plant)

    @Upsert
    suspend fun upsertAll(plants: List<Plant>)

    @Query("DELETE FROM Plant")
    suspend fun clear()

    @Query("SELECT * FROM Plant")
    fun getAll(): Flow<List<Plant>>
}
