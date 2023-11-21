package com.technical_challenge.github.ui.mapper

import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel

fun GithubRepositoryDomainModel.toGithubRepositoryUIModel() =
    GithubRepositoryUIModel(
        id = id,
        name = name,
        stars = stars
    )

fun GithubRepositoryUIModel.toGithubRepositoryDomainModel() =
    GithubRepositoryDomainModel(
        id = id,
        name = name,
        stars = stars
    )
