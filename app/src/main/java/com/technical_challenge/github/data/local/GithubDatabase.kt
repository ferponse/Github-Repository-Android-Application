package com.technical_challenge.github.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.local.model.GithubRepositoryRemoteKeysEntity

@Database(
    entities = [GithubRepositoryEntity::class, GithubRepositoryRemoteKeysEntity::class],
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {

    abstract val githubRepositoryDao: GithubRepositoryDao
    abstract val githubRepositoryRemoteKeysDao: GithubRepositoryRemoteKeysDao

}