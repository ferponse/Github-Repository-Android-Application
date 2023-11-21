package com.technical_challenge.github.domain.model


data class GithubRepositoryDomainModel(
    val id: Long,
    val name: String,
    val stars: Int
)