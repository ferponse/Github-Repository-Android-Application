package com.technical_challenge.github.data.remote

import com.technical_challenge.github.data.remote.model.GithubRepositoryDTO
import com.technical_challenge.github.data.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiClient {

    @GET(Constants.END_POINT)
    suspend fun getGithubRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GithubRepositoryDTO>


}