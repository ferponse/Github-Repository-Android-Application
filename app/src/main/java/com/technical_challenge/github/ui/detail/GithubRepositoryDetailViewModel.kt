package com.technical_challenge.github.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technical_challenge.github.domain.usecase.UpdateGithubRepositoryUseCase
import com.technical_challenge.github.kotlin_utils.isSuccess
import com.technical_challenge.github.ui.di.IoDispatcher
import com.technical_challenge.github.ui.mapper.toGithubRepositoryDomainModel
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubRepositoryDetailViewModel @Inject constructor(
    val data: GithubRepositoryDetailData,
    val event: GithubRepositoryDetailEvent,
    private val updateGithubRepositoryUseCase: UpdateGithubRepositoryUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var githubRepositoryUIModel: GithubRepositoryUIModel

    private fun updateGithubRepository(githubRepositoryUIModel: GithubRepositoryUIModel) {
        viewModelScope.launch(ioDispatcher) {
            data.showLoading()
            delay(4000)
            val result = updateGithubRepositoryUseCase(githubRepositoryUIModel.toGithubRepositoryDomainModel())
            if (result.isSuccess()) {
                event.sendSuccessWhenUpdatingGithubRepositoryDataEvent()
            } else {
                event.sendErrorWhenUpdatingGithubRepositoryDataEvent()
            }
            data.hideLoading()
        }
    }

    internal fun loadGithubRepository(githubRepository: GithubRepositoryUIModel) {
        githubRepositoryUIModel = githubRepository
        data.updateGithubRepositoryData(githubRepository)
    }

    internal fun onSaveButtonClick() {
        updateGithubRepository(
            githubRepositoryUIModel.copy(
                name = data.name.value.toString()
            )
        )
    }


}