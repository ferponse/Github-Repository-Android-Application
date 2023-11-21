package com.technical_challenge.github.data.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.technical_challenge.github.data.GithubRepositoryRemoteMediator
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.remote.GithubApiClient
import com.technical_challenge.github.domain.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PagingModule {
    @Singleton
    @Provides
    fun provideRepositoryRemoteMediator(
        githubDatabase: GithubDatabase,
        githubApiClient: GithubApiClient
    ): GithubRepositoryRemoteMediator {
        return GithubRepositoryRemoteMediator(
            githubDatabase = githubDatabase,
            githubApiClient = githubApiClient
        )
    }

    @Singleton
    @Provides
    fun providePagingConfig(): PagingConfig {
        return PagingConfig(
            initialLoadSize = 1,
            pageSize = Constants.PAGE_SIZE,
            maxSize = Constants.MAX_PAGER_REPOSITORY_SIZE,
            enablePlaceholders = false
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Singleton
    @Provides
    fun providePager(
        githubDatabase: GithubDatabase,
        githubRepositoryRemoteMediator: GithubRepositoryRemoteMediator,
        githubRepositoryPagingConfig: PagingConfig
    ): Pager<Int, GithubRepositoryEntity> {
        return Pager(
            config = githubRepositoryPagingConfig,
            remoteMediator = githubRepositoryRemoteMediator,
            pagingSourceFactory = { githubDatabase.githubRepositoryDao.pagingSource() }
        )
    }
}