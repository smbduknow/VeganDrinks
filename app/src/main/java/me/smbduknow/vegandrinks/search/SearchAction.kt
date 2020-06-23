package me.smbduknow.vegandrinks.search

import me.smbduknow.vegandrinks.data.model.Product

sealed class SearchAction {
    class StartSearch(val query: String) : SearchAction()
    class SelectProduct(product: Product) : SearchAction()
}
