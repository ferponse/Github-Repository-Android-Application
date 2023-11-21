package com.technical_challenge.github.di

import android.content.Context
import com.technical_challenge.github.android_utils.di.ApplicationContext
import com.technical_challenge.github.android_utils.di.ViewModelFactoryModule
import com.technical_challenge.github.GithubApplication
import com.technical_challenge.github.data.di.GithubNetworkModule
import com.technical_challenge.github.android_utils.di.DispatchersModule
import com.technical_challenge.github.data.di.PagingModule
import com.technical_challenge.github.repository.RepositoryModule
import com.technical_challenge.github.ui.di.GithubUIComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    GithubNetworkModule::class,
    DispatchersModule::class,
    PagingModule::class,
    RepositoryModule::class
])
interface ApplicationComponent: AndroidInjector<GithubApplication> {

    fun plusGithubComponentBuilder(): GithubUIComponent.Builder

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun githubApplication(application: GithubApplication): Builder
        @BindsInstance
        fun applicationContext(@ApplicationContext context: Context): Builder

    }
}