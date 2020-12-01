package me.smbduknow.vegandrinks.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.smbduknow.vegandrinks.feature.search.domain.SearchRepository

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository

    fun dispatch(action: SearchAction) {
        handleAction(action)
    }

    private fun handleAction(action: SearchAction) = when (action) {
        is SearchAction.StartSearch -> doSearch(action.query)
    }

    private fun doSearch(query: String) {
        viewModelScope.launch {
            repository.searchResults.value = repository.search(query)
        }
    }
}
