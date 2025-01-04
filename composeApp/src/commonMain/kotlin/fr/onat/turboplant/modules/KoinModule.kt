package fr.onat.turboplant.modules

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.database.AppDatabase
import fr.onat.turboplant.data.database.getRoomDatabase
import fr.onat.turboplant.data.repositories.AuthRepository
import fr.onat.turboplant.data.repositories.PlantsRepository
import fr.onat.turboplant.data.repositories.RoomsRepository
import fr.onat.turboplant.data.repositories.TasksRepository
import fr.onat.turboplant.presentation.login.LoginViewModel
import fr.onat.turboplant.presentation.plants.PlantsViewModel
import fr.onat.turboplant.presentation.rooms.RoomsViewModel
import fr.onat.turboplant.presentation.tasks.TasksViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            provideDataSourceModule,
            provideRepositoryModule,
            provideApiModule,
            provideViewModelModule,
            provideHttpClient,
            providePlatformModule,
            provideDaoModule
        )
    }

val provideDataSourceModule = module {
    single {
        getRoomDatabase(get())
    }
}

val provideHttpClient = module {
    single {
        HttpClient {
            install(ContentNegotiation)
            {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
        }
    }
}

val provideRepositoryModule = module {
    singleOf(::AuthRepository)
    singleOf(::PlantsRepository)
    singleOf(::RoomsRepository)
    singleOf(::TasksRepository)
}

val provideDaoModule = module {
    single { get<AppDatabase>().getUserDao() }
    single { get<AppDatabase>().getPlantDao() }
    single { get<AppDatabase>().getTaskDao() }
    single { get<AppDatabase>().getPlaceDao() }
    single { get<AppDatabase>().getRoomDao() }
}

val provideApiModule = module {
    singleOf(::ArchiApi)
}

val provideViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::PlantsViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::RoomsViewModel)
}

expect val providePlatformModule: Module