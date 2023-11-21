package com.technical_challenge.github.ui.list

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class RepositoryListEvent @Inject constructor() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    internal suspend fun sendSuccessWhenDeletingGithubRepositoryEvent() {
        _event.emit(Event.SuccessWhenDeletingGithubRepository)
    }

    internal suspend fun sendErrorWhenDeletingGithubRepositoryEvent() {
        _event.emit(Event.ErrorWhenDeletingGithubRepository)
    }

    sealed class Event {
        data object SuccessWhenDeletingGithubRepository : Event()
        data object ErrorWhenDeletingGithubRepository : Event()
    }
}