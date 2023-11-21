package com.technical_challenge.github.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel

class RepositoryListData(
    var githubRepositoryPagingData: LiveData<PagingData<GithubRepositoryUIModel>> = MutableLiveData(),
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
) {
    internal fun updateGithubRepositoryPagingData(githubRepositoryPagingData: LiveData<PagingData<GithubRepositoryUIModel>>) {
        this.githubRepositoryPagingData = githubRepositoryPagingData
    }

    internal fun showLoading() {
        this.isLoading.postValue(true)
    }

    internal fun hideLoading() {
        this.isLoading.postValue(false)
    }
}