package com.technical_challenge.github.domain.usecase

import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.domain.repository.GithubRepository
import javax.inject.Inject

class UpdateGithubRepositoryUseCase @Inject constructor(
private val repository: GithubRepository
) {
    suspend operator fun invoke(githubRepository: GithubRepositoryDomainModel) =
        this.repository.updateGithubRepository(githubRepository)
}