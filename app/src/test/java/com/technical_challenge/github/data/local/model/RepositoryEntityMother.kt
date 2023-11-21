package com.technical_challenge.github.data.local.model

object RepositoryEntityMother {
    fun mock(id: Long = 1L): GithubRepositoryEntity {
        return GithubRepositoryEntity(
            id = id,
            name = "attachment_fu",
            stars = 10
        )
    }
}