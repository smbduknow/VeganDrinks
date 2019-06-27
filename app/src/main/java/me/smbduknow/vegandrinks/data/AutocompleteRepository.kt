package me.smbduknow.vegandrinks.data

import io.reactivex.Single

class AutocompleteRepository {

    private val source = RemoteDataSource

    fun autocomplete(query: String): Single<List<String>> =
        source.getAutocompleteResults(query)
}