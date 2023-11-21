package com.technical_challenge.github.ui.di

import androidx.lifecycle.ViewModel
import com.technical_challenge.github.android_utils.di.ViewModelKey
import com.technical_challenge.github.ui.detail.GithubRepositoryDetailViewModel
import com.technical_challenge.github.ui.list.GithubRepositoryListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface GithubViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GithubRepositoryListViewModel::class)
    fun bindRepositoryListViewModel(viewModel: GithubRepositoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GithubRepositoryDetailViewModel::class)
    fun bindRepositoryDetailViewModel(viewModel: GithubRepositoryDetailViewModel): ViewModel
}