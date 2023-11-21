package com.technical_challenge.github.ui.di

import com.technical_challenge.github.ui.detail.GithubRepositoryDetailData
import com.technical_challenge.github.ui.list.RepositoryListData
import dagger.Module
import dagger.Provides

@Module
object UIDataModule {
    @Provides
    @GithubScope
    fun provideRepositoryListData() = RepositoryListData()
    @Provides
    @GithubScope
    fun provideRepositoryDetailData() = GithubRepositoryDetailData()
}