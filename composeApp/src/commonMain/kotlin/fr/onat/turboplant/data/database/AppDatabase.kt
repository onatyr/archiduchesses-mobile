package fr.onat.turboplant.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.SQLiteDriver
import fr.onat.turboplant.data.dao.PlaceDao
import fr.onat.turboplant.data.dao.PlantDao
import fr.onat.turboplant.data.dao.RoomDao
import fr.onat.turboplant.data.dao.TaskDao
import fr.onat.turboplant.data.dao.UserDao
import fr.onat.turboplant.data.database.typeConverters.InstantTypeConverter
import fr.onat.turboplant.data.entities.Place
import fr.onat.turboplant.data.entities.Plant
import fr.onat.turboplant.data.entities.Room
import fr.onat.turboplant.data.entities.Task
import fr.onat.turboplant.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [
        User::class,
        Plant::class,
        Task::class,
        Place::class,
        Room::class
    ],
    version = 1
)
@TypeConverters(InstantTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getPlantDao(): PlantDao
    abstract fun getTaskDao(): TaskDao
    abstract fun getPlaceDao(): PlaceDao
    abstract fun getRoomDao(): RoomDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

expect fun getSQLiteDriver(): SQLiteDriver

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(getSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}