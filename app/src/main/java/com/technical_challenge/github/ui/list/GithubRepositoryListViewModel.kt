package com.technical_challenge.github.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.technical_challenge.github.domain.usecase.DeleteGithubRepositoryUseCase
import com.technical_challenge.github.domain.usecase.GetGithubRepositoriesUseCase
import com.technical_challenge.github.kotlin_utils.isSuccess
import com.technical_challenge.github.ui.di.IoDispatcher
import com.technical_challenge.github.ui.mapper.toGithubRepositoryUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class GithubRepositoryListViewModel @Inject constructor(
    val data: RepositoryListData,
    val event: RepositoryListEvent,
    getGithubRepositoriesUseCase: GetGithubRepositoriesUseCase,
    private val deleteGithubRepositoryUseCase: DeleteGithubRepositoryUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        val githubRepositoryPagingData = getGithubRepositoriesUseCase()
            .map { pagingData ->
                pagingData.map { githubRepositoryDomainModel -> githubRepositoryDomainModel.toGithubRepositoryUIModel() }
            }
            .cachedIn(viewModelScope)
            .asLiveData(viewModelScope.coroutineContext + ioDispatcher)
        data.updateGithubRepositoryPagingData(githubRepositoryPagingData)
    }

    internal fun deleteGithubRepository(id: Long) {
        viewModelScope.launch(ioDispatcher) {
            data.showLoading()
            delay(4000)
            val result = deleteGithubRepositoryUseCase(id)
            if (result.isSuccess()) {
                event.sendSuccessWhenDeletingGithubRepositoryEvent()
            } else {
                event.sendErrorWhenDeletingGithubRepositoryEvent()
            }
            data.hideLoading()
        }
    }


}