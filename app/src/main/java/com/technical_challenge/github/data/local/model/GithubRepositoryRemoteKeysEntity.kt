package com.technical_challenge.github.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.technical_challenge.github.data.utils.Constants

@Entity(tableName = Constants.GITHUB_REPOSITORY_REMOTE_KEYS_TABLE_NAME)

data class GithubRepositoryRemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?
)
