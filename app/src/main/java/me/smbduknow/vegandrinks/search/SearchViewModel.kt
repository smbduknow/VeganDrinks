package me.smbduknow.vegandrinks.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import me.smbduknow.vegandrinks.data.SearchRepository
import me.smbduknow.vegandrinks.data.exception.NoConnectionException
import me.smbduknow.vegandrinks.data.model.Product

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()

    private val actionChannel = Channel<SearchAction>()

    val viewState = actionChannel.consumeAsFlow()
        .flatMapLatest(::handleAction)
        .onStart { emit(SearchViewState.Initial) }
        .asLiveData(Dispatchers.Default)

    fun dispatch(action: SearchAction) {
        actionChannel.offer(action)
    }

    private suspend fun handleAction(action: SearchAction): Flow<SearchViewState> = when (action) {
        is SearchAction.StartSearch -> doSearch(action.query)
        else -> emptyFlow()
    }

    private suspend fun doSearch(query: String): Flow<SearchViewState> =
        repository.search(query)
            .map { result -> mapResultItems(result) }
            .catch { error -> emit(mapError(error)) }
            .onStart { emit(SearchViewState.Loading) }

    private fun mapResultItems(items: List<Product>) = when {
        items.isNotEmpty() -> SearchViewState.Content(items)
        else -> SearchViewState.NoResults
    }

    private fun mapError(e: Throwable) = when (e) {
        is NoConnectionException -> SearchViewState.NoConnection
        else -> SearchViewState.Error
    }
}
