package com.onoffrice.dogsbreed.data.di

import com.onoffrice.dogsbreed.data.repositories.Repository
import com.onoffrice.dogsbreed.data.repositories.RepositoryImp
import com.onoffrice.dogsbreed.ui.dogsFeed.DogsFeedViewModel
import com.onoffrice.dogsbreed.ui.main.DogsSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModulesInjector {

    val dogSearchModule = module {
        viewModel { DogsSearchViewModel(get()) }
        single<Repository> { RepositoryImp(get()) }
    }

    val feedModule = module {
        viewModel { DogsFeedViewModel(get()) }
    }
}