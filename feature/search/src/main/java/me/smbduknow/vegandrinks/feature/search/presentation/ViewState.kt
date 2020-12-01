package me.smbduknow.vegandrinks.feature.search.presentation

import me.smbduknow.vegandrinks.feature.search.domain.model.Product

internal sealed class ViewState {
    class Content(val items: List<Product>) : ViewState()
    object Initial : ViewState()
    object Loading : ViewState()
    object NoConnection : ViewState()
    object NoResults : ViewState()
    object Error : ViewState()
}
