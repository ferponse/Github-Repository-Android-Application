package com.technical_challenge.github.data.remote.model

import com.google.gson.annotations.SerializedName

data class GithubRepositoryDTO(
    val id: Long,
    val name: String,
    @SerializedName("stargazers_count") val stars: Int
)