package com.technical_challenge.github.android_utils.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
object ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        providerMap: MutableMap<Class<out ViewModel>, Provider<ViewModel>>,
    ): ViewModelProvider.Factory {
        return ViewModelFactory(providerMap)
    }

}