package fr.onat.turboplant.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.android.inject


actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context: Context by inject()
    return with(context) {
        val dbFile = applicationContext.getDatabasePath("turbo_database.db")
        Room.databaseBuilder<AppDatabase>(
            context = applicationContext,
            name = dbFile.absolutePath
        )
    }
}