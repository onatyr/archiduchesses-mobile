package fr.onat.turboplant.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import fr.onat.turboplant.data.models.entities.Plant
import fr.onat.turboplant.data.models.entities.PlantWithRoom
import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
@Dao
interface PlantDao {
    @Upsert
    suspend fun upsert(plant: Plant)

    @Upsert
    suspend fun upsertAll(plants: List<Plant>)

    @Query("DELETE FROM Plant")
    suspend fun clear()

    @Query("SELECT * FROM Plant")
    fun getAllWithRoom(): Flow<List<PlantWithRoom>>

    @Query(
        """SELECT * FROM Plant
            WHERE roomId = :id"""
    )
    fun getPlantsByRoomId(id: String): Flow<List<Plant>>
}
