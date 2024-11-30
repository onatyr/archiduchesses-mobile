package fr.onat.turboplant.modules

import androidx.room.RoomDatabase
import fr.onat.turboplant.data.database.AppDatabase
import fr.onat.turboplant.data.database.getDatabaseBuilder
import org.koin.dsl.bind
import org.koin.dsl.module

actual val providePlatformModule = module {
    single { getDatabaseBuilder() }.bind<RoomDatabase.Builder<AppDatabase>>()
}