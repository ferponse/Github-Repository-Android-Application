package com.technical_challenge.github.repository

import androidx.paging.Pager
import androidx.paging.map
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.mapper.toRepositoryResponseDomainModel
import com.technical_challenge.github.domain.DomainError
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.domain.repository.GithubRepository
import com.technical_challenge.github.kotlin_utils.Either
import com.technical_challenge.github.kotlin_utils.buildFailure
import com.technical_challenge.github.kotlin_utils.buildSuccess
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val githubDatabase: GithubDatabase,
    private val pager: Pager<Int, GithubRepositoryEntity>
) : GithubRepository {

    override fun getGithubRepositories() =
        pager.flow.map { pagingData ->
            pagingData.map { githubRepositoryEntity -> githubRepositoryEntity.toRepositoryResponseDomainModel() }
        }

    override suspend fun deleteGithubRepositoryById(githubRepository: Long): Either<Unit, DomainError> =
        try {
            with(githubDatabase.githubRepositoryDao) {
                deleteGithubRepositoryById(githubRepository)
                pagingSource().invalidate()
            }
            buildSuccess(Unit)
        } catch (e: Exception) {
            buildFailure(DomainError(error = e.toString()))
        }

    override suspend fun updateGithubRepository(githubRepository: GithubRepositoryDomainModel): Either<Unit, DomainError> =
        try {
            with(githubDatabase.githubRepositoryDao) {
                this@with.updateGithubRepository(githubRepository)
                pagingSource().invalidate()
            }
            buildSuccess(Unit)
        } catch (e: Exception) {
            buildFailure(DomainError(error = e.toString()))
        }
}