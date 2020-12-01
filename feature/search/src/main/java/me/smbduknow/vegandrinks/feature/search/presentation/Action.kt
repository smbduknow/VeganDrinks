package me.smbduknow.vegandrinks.feature.search.presentation

import me.smbduknow.vegandrinks.feature.search.domain.model.Product

sealed class Action {
    class SelectProduct(product: Product) : Action()
}
