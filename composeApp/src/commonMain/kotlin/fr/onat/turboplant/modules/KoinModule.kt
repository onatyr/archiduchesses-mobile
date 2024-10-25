package fr.onat.turboplant.modules

import fr.onat.turboplant.api.ArchiApi
import fr.onat.turboplant.repositories.PlantRepository
import fr.onat.turboplant.viewModels.LoginViewModel
import fr.onat.turboplant.viewModels.PlantListViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            provideRepositoryModule,
            provideApiModule,
            provideViewModelModule
        )
    }

val provideDataSourceModule = module {
//    singleOf(::NoteLocalDataSourceImpl).bind(NoteLocalDataSource::class)
}

val provideRepositoryModule = module {
    singleOf(::PlantRepository)
}

val provideApiModule = module {
    singleOf(::ArchiApi)
}

val provideViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::PlantListViewModel)
}