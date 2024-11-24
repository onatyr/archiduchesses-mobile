package fr.onat.turboplant.modules

import fr.onat.turboplant.data.api.ArchiApi
import fr.onat.turboplant.data.repositories.AuthRepository
import fr.onat.turboplant.data.repositories.PlaceRepository
import fr.onat.turboplant.data.repositories.PlantRepository
import fr.onat.turboplant.presentation.viewModels.LoginViewModel
import fr.onat.turboplant.presentation.viewModels.PlantListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            provideRepositoryModule,
            provideApiModule,
            provideViewModelModule,
            provideHttpClient
        )
    }

val provideDataSourceModule = module {
//    singleOf(::NoteLocalDataSourceImpl).bind(NoteLocalDataSource::class)
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
    singleOf(::PlantRepository)
    singleOf(::PlaceRepository)
}

val provideApiModule = module {
    singleOf(::ArchiApi)
}

val provideViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::PlantListViewModel)
}