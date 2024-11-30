package fr.onat.turboplant.data.database

import android.content.Context
import androidx.room.Room


fun getDatabaseBuilder(
    context: Context
) = with(context) {
    val dbFile = applicationContext.getDatabasePath("turbo_database.db")
    Room.databaseBuilder<AppDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath
    )
}