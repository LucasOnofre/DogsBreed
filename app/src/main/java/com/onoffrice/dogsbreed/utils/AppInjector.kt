package com.onoffrice.dogsbreed.utils

import com.onoffrice.dogsbreed.data.repositories.RepositoryImp
import com.onoffrice.dogsbreed.ui.dogsFeed.DogsFeedViewModel
import com.onoffrice.dogsbreed.ui.main.DogsSearchViewModel


object AppInjector {

    //VIEW MODELS
    fun getDogsSearchViewModel() = DogsSearchViewModel.Factory(RepositoryImp())

    fun getDogsFeedViewModel() = DogsFeedViewModel.Factory(RepositoryImp())

}
