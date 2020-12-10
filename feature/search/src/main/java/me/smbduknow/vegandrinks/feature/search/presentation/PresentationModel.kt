package me.smbduknow.vegandrinks.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.smbduknow.vegandrinks.feature.search.domain.SearchRepository
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

internal class PresentationModel : ViewModel() {

    private val repository = SearchRepository

    private val actionChannel = Channel<Action>()

    val viewState = observeActions()
        .stateIn(viewModelScope, SharingStarted.Lazily, ViewState.Initial)

    fun dispatch(action: Action) {
        actionChannel.offer(action)
    }

    private fun observeActions(): Flow<ViewState> =
        actionChannel.consumeAsFlow()
            .mapNotNull { action -> execute(viewState.value, action) }
            .map { action -> reduce(viewState.value, action)  }

    private fun reduce(currentState: ViewState, action: Action): ViewState =
        when (action) {
            is Action.StartSearch -> ViewState.Loading
            is Action.Result -> mapResultItems(action.data)
            is Action.Error -> ViewState.Error
            else -> currentState
        }

    private fun execute(currentState: ViewState, action: Action): Action? {
        if (action is Action.StartSearch) {
            viewModelScope.launch {
                dispatch(doSearch(action.query))
            }
        }
        return action.takeUnless { it is Action.SelectProduct }
    }

    private suspend fun doSearch(query: String): Action =
        try {
            repository.search(query).let(Action::Result)
        } catch (e: Throwable) {
            Action.Error(e)
        }

    // mappers

    private fun mapResultItems(items: List<Product>): ViewState = when {
        items.isNotEmpty() -> ViewState.Content(items)
        else -> ViewState.NoResults
    }
}
