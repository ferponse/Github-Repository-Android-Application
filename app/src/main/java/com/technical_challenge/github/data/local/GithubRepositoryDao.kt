package com.technical_challenge.github.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.mapper.toGithubRepositoryEntity
import com.technical_challenge.github.data.utils.Constants.GITHUB_REPOSITORY_TABLE_NAME
import com.technical_challenge.github.domain.model.GithubRepositoryDomainModel

@Dao
interface GithubRepositoryDao {

    @Upsert
    suspend fun upsertAllGithubRepositories(githubRepositories: List<GithubRepositoryEntity>)

    @Query("DELETE FROM $GITHUB_REPOSITORY_TABLE_NAME WHERE id = :githubRepositoryId")
    suspend fun deleteGithubRepositoryById(githubRepositoryId: Long)

    @Update
    suspend fun updateGithubRepositoryEntity(githubRepositoryEntity: GithubRepositoryEntity)

    suspend fun updateGithubRepository(githubRepository: GithubRepositoryDomainModel) {
        val githubRepositoryEntity = githubRepository.toGithubRepositoryEntity()
        updateGithubRepositoryEntity(githubRepositoryEntity)
    }

    @Query("SELECT * FROM $GITHUB_REPOSITORY_TABLE_NAME")
    fun pagingSource(): PagingSource<Int, GithubRepositoryEntity>

    @Query("DELETE FROM $GITHUB_REPOSITORY_TABLE_NAME")
    suspend fun clearAll()

}