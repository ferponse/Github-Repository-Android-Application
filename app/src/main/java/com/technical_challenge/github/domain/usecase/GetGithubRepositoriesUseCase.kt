package com.technical_challenge.github.domain.usecase

import androidx.paging.PagingData
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubRepositoriesUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(): Flow<PagingData<GithubRepositoryDomainModel>> =
        repository.getGithubRepositories()
}