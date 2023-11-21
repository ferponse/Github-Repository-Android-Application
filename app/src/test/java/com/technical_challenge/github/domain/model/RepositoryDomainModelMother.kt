package com.technical_challenge.github.domain.model

object RepositoryDomainModelMother {
    fun mock(id: Long = 1L) = GithubRepositoryDomainModel(
        id = id,
        name = "attachment_fu",
        stars = 10
    )
}