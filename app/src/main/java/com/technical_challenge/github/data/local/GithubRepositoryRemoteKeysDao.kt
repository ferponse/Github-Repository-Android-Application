package com.technical_challenge.github.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.technical_challenge.github.data.local.model.GithubRepositoryRemoteKeysEntity
import com.technical_challenge.github.data.utils.Constants

@Dao
interface GithubRepositoryRemoteKeysDao {

    @Upsert
    suspend fun upsertAllRemoteKeys(remoteKeys: List<GithubRepositoryRemoteKeysEntity>)

    @Query("SELECT * FROM ${Constants.GITHUB_REPOSITORY_REMOTE_KEYS_TABLE_NAME} WHERE id = :id")
    fun getRemoteKeys(id: Long): GithubRepositoryRemoteKeysEntity

    @Query("DELETE FROM ${Constants.GITHUB_REPOSITORY_REMOTE_KEYS_TABLE_NAME}")
    suspend fun clearAll()

}