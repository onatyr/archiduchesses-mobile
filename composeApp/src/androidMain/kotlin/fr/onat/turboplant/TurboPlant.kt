package fr.onat.turboplant

import android.app.Application
import fr.onat.turboplant.modules.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class TurboPlant: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@TurboPlant)
        }
    }
}