package com.technical_challenge.github.ui.detail

import androidx.lifecycle.MutableLiveData
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel

class GithubRepositoryDetailData(
    val name: MutableLiveData<String> = MutableLiveData<String>(EMPTY_STRING),
    val stars: MutableLiveData<String> = MutableLiveData<String>(EMPTY_STRING),
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
) {

    internal fun updateGithubRepositoryData(githubRepository: GithubRepositoryUIModel) {
        this.name.postValue(githubRepository.name)
        this.stars.postValue(githubRepository.stars.toString())
    }

    internal fun showLoading() {
        this.isLoading.postValue(true)
    }

    internal fun hideLoading() {
        this.isLoading.postValue(false)
    }

    companion object {
        private const val EMPTY_STRING = ""
    }
}