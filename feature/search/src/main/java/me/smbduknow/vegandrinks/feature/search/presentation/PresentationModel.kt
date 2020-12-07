package me.smbduknow.vegandrinks.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import me.smbduknow.vegandrinks.feature.search.domain.SearchRepository
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

internal class PresentationModel : ViewModel() {

    private val repository = SearchRepository

    private val actionChannel = Channel<Action>()

    private val viewState = repository.observeSearchResults()
        .map { mapResultItems(it)  }
        .catch { mapError(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState.Initial)


    fun observe(): Flow<ViewState> = viewState

    fun dispatch(action: Action) {
        actionChannel.offer(action)
    }

    // mappers

    private fun mapResultItems(items: List<Product>): ViewState = when {
        items.isNotEmpty() -> ViewState.Content(items)
        else -> ViewState.NoResults
    }

    private fun mapError(e: Throwable) = when (e) {
        else -> ViewState.Error
    }
}
