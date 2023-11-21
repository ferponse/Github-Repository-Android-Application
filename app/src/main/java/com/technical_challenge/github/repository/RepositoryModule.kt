package com.technical_challenge.github.repository

import androidx.paging.Pager
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.GithubRepositoryDao
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryDao(database: GithubDatabase): GithubRepositoryDao {
        return database.githubRepositoryDao
    }

    @Singleton
    @Provides
    fun providesGithubRepository(
        githubDatabase: GithubDatabase,
        pager: Pager<Int, GithubRepositoryEntity>
    ): GithubRepository {
        return GithubRepositoryImpl(
            githubDatabase = githubDatabase,
            pager = pager
        )
    }

}