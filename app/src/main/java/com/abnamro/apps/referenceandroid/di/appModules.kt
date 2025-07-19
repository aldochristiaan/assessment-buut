package com.abnamro.apps.referenceandroid.di

import com.abnamro.apps.referenceandroid.network.CommentsAPIService
import com.abnamro.apps.referenceandroid.repository.CommentsRepository
import com.abnamro.apps.referenceandroid.repository.CommentsRepositoryImpl
import com.abnamro.apps.referenceandroid.viewmodel.CommentsViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModules = module {
    //API Service dependency
    single<CommentsAPIService> { CommentsAPIService() }
    //coroutine dispatcher dependency
    single { Dispatchers.IO }

    //repository dependency
    single<CommentsRepository> {
        CommentsRepositoryImpl(
            apiService = get(),
            dispatcher = get()
        )
    }
    //viewmodel dependency
    single { CommentsViewModel(repository = get()) }

}
