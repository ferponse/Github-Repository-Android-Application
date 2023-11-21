package com.technical_challenge.github.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.technical_challenge.github.data.utils.Constants.GITHUB_REPOSITORY_TABLE_NAME

@Entity(tableName = GITHUB_REPOSITORY_TABLE_NAME)
data class GithubRepositoryEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val stars: Int
)