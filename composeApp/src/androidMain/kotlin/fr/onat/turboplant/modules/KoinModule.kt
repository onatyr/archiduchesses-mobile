package fr.onat.turboplant.modules

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val provideAndroidModule = module {
    single { androidContext() }
}