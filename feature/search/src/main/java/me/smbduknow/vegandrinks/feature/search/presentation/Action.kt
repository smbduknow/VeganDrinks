package me.smbduknow.vegandrinks.feature.search.presentation

import me.smbduknow.vegandrinks.feature.search.domain.model.Product

sealed class Action {
    data class StartSearch(val query: String) : Action()
    data class Result(val data: List<Product>) : Action()
    data class Error(val error: Throwable) : Action()
    data class SelectProduct(val product: Product) : Action()
}
