package com.technical_challenge.github.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.technical_challenge.github.data.local.GithubDatabase
import com.technical_challenge.github.data.local.model.GithubRepositoryEntity
import com.technical_challenge.github.data.local.model.GithubRepositoryRemoteKeysEntity
import com.technical_challenge.github.data.mapper.toGithubRepositoryEntity
import com.technical_challenge.github.data.remote.GithubApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GithubRepositoryRemoteMediator(
    private val githubDatabase: GithubDatabase,
    private val githubApiClient: GithubApiClient
) : RemoteMediator<Int, GithubRepositoryEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GithubRepositoryEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = withContext(Dispatchers.IO) {
                githubApiClient.getGithubRepositories(page = currentPage, perPage = state.config.pageSize)
            }
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            // simulate a delay
            delay(4000)

            withContext(Dispatchers.IO) {
                githubDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        githubDatabase.githubRepositoryDao.clearAll()
                        githubDatabase.githubRepositoryRemoteKeysDao.clearAll()
                    }

                    // work with remote keys
                    val keys = response.map { unsplashImage ->
                        GithubRepositoryRemoteKeysEntity(
                            id = unsplashImage.id,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }
                    githubDatabase.githubRepositoryRemoteKeysDao.upsertAllRemoteKeys(remoteKeys = keys)

                    // work with github repositories
                    val githubRepositoryEntities = response.map { githubRepositoryDTO ->
                        githubRepositoryDTO.toGithubRepositoryEntity()
                    }
                    githubDatabase.githubRepositoryDao.upsertAllGithubRepositories(
                        githubRepositoryEntities
                    )
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, GithubRepositoryEntity>
    ): GithubRepositoryRemoteKeysEntity? =
        withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    githubDatabase.githubRepositoryRemoteKeysDao.getRemoteKeys(id = id)
                }
            }
        }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, GithubRepositoryEntity>
    ): GithubRepositoryRemoteKeysEntity? =
        withContext(Dispatchers.IO) {
            state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                ?.let { unsplashImage ->
                    githubDatabase.githubRepositoryRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
                }
        }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GithubRepositoryEntity>
    ): GithubRepositoryRemoteKeysEntity? =
        withContext(Dispatchers.IO) {
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                ?.let { unsplashImage ->
                    githubDatabase.githubRepositoryRemoteKeysDao.getRemoteKeys(id = unsplashImage.id)
                }
        }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}