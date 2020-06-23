package me.smbduknow.vegandrinks.search

import me.smbduknow.vegandrinks.data.model.Product

sealed class SearchViewState {
    class Content(val items: List<Product>) : SearchViewState()
    object Initial : SearchViewState()
    object Loading : SearchViewState()
    object NoConnection : SearchViewState()
    object NoResults : SearchViewState()
    object Error : SearchViewState()
}
