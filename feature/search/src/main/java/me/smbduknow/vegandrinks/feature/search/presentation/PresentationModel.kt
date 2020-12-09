package me.smbduknow.vegandrinks.feature.search.presentation

import android.util.Log
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

    private val viewState = observeActions()
        .onEach { Log.d("FLOW", it.toString()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState.Initial)

    fun observe(): Flow<ViewState> = viewState

    fun dispatch(action: Action) {
        actionChannel.offer(action)
    }

    private fun observeSearch(query: String): Flow<Action> =
        repository.search(query)
            .map { Action.Result(it) }
            .catch { Action.Error(it) }

    private fun observeActions(): Flow<ViewState> {
        return actionChannel.consumeAsFlow()
            .onEach { action ->
               if(action is Action.StartSearch) {
                   viewModelScope.launch {
                       observeSearch(action.query)
                           .collect { dispatch(it) }
                   }
               }
            }
            .map { // reducer
                when (it) {
                    is Action.StartSearch -> ViewState.Loading
                    is Action.Result -> mapResultItems(it.data)
                    is Action.Error -> ViewState.Error
                    is Action.SelectProduct -> viewState.value
                }
            }
    }

    // mappers

    private fun mapResultItems(items: List<Product>): ViewState = when {
        items.isNotEmpty() -> ViewState.Content(items)
        else -> ViewState.NoResults
    }
}
