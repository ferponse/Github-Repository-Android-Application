package com.technical_challenge.github.ui.detail

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class GithubRepositoryDetailEvent @Inject constructor() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    internal suspend fun sendSuccessWhenUpdatingGithubRepositoryDataEvent() {
        _event.emit(Event.SuccessWhenUpdatingGithubRepositoryData)
    }
    internal suspend fun sendErrorWhenUpdatingGithubRepositoryDataEvent() {
        _event.emit(Event.ErrorWhenUpdatingGithubRepositoryData)
    }

    sealed class Event {
        data object SuccessWhenUpdatingGithubRepositoryData : Event()
        data object ErrorWhenUpdatingGithubRepositoryData : Event()
    }
}