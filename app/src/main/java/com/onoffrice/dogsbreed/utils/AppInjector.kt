package com.onoffrice.dogsbreed.utils

import com.onoffrice.dogsbreed.data.repositories.RepositoryImp
import com.onoffrice.dogsbreed.ui.main.DogsSearchViewModel


object AppInjector {

    //VIEW MODELS
    fun getDogsSearchViewModel() = DogsSearchViewModel.Factory(RepositoryImp())

}
