package me.smbduknow.vegandrinks.feature.search.domain.model

data class Product(
    val id: Int,
    val name: String,
    val status: Status,
    val type: Type,
    val country: String,
    val company: Company
) {

    enum class Status {
        VEGAN, NOT_VEGAN, UNKNOWN
    }

    enum class Type {
        BEER, WINE, LIQUOR, OTHER
    }
}
