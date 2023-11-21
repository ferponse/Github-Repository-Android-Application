package com.technical_challenge.github.data.mapper

import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.remote.model.GithubRepositoryDTO
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel

fun GithubRepositoryDTO.toGithubRepositoryEntity() =
    GithubRepositoryEntity(
        id = id,
        name = name,
        stars = stars
    )

fun GithubRepositoryDomainModel.toGithubRepositoryEntity() =
    GithubRepositoryEntity(
        id = id,
        name = name,
        stars = stars
    )

fun GithubRepositoryEntity.toRepositoryResponseDomainModel() =
    GithubRepositoryDomainModel(
        id = id,
        name = name,
        stars = stars
    )
