package com.technical_challenge.github.domain.repository

import androidx.paging.PagingData
import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.kotlin_utils.Either
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubRepositories(): Flow<PagingData<GithubRepositoryDomainModel>>
    suspend fun deleteGithubRepositoryById(githubRepository: Long): Either<Unit, DomainError>
    suspend fun updateGithubRepository(githubRepository: GithubRepositoryDomainModel): Either<Unit, DomainError>
}