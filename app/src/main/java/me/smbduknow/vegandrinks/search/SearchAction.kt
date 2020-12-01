package me.smbduknow.vegandrinks.search

sealed class SearchAction {
    class StartSearch(val query: String) : SearchAction()
}
