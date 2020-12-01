package me.smbduknow.vegandrinks.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.smbduknow.vegandrinks.feature.search.domain.SearchRepository
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

internal class PresentationModel : ViewModel() {

    private val repository = SearchRepository

    private val actionChannel = Channel<Action>()
    private val viewState = MutableStateFlow<ViewState>(ViewState.Initial)

    init {
        viewModelScope.launch {
            repository.searchResults.collect {
                viewState.value = mapResultItems(it)
            }
        }
    }

    fun observe(): StateFlow<ViewState> = viewState

    fun dispatch(action: Action) {
        actionChannel.offer(action)
    }

    // mappers

    private fun mapResultItems(items: List<Product>) = when {
        items.isNotEmpty() -> ViewState.Content(items)
        else -> ViewState.NoResults
    }

    private fun mapError(e: Throwable) = when (e) {
        else -> ViewState.Error
    }
}
