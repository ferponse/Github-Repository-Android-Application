package com.technical_challenge.github.domain.usecase

import com.technical_challenge.github.domain.repository.GithubRepository
import javax.inject.Inject

class DeleteGithubRepositoryUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteGithubRepositoryById(id)

}