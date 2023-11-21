package com.technical_challenge.github.ui.di

import com.technical_challenge.github.ui.detail.GithubRepositoryDetailFragment
import com.technical_challenge.github.ui.list.GithubRepositoryListFragment
import dagger.Subcomponent

@GithubScope
@Subcomponent(modules = [GithubViewModelModule::class, UIDataModule::class])
interface GithubUIComponent {
    fun inject(fragment: GithubRepositoryListFragment)
    fun inject(fragment: GithubRepositoryDetailFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): GithubUIComponent
    }
}

interface GithubProvider {
    fun provideGithubComponent(): GithubUIComponent
}