package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.smbduknow.vegandrinks.data.api.BarnivoreApi
import me.smbduknow.vegandrinks.data.api.BarnivoreApiFactory
import me.smbduknow.vegandrinks.data.model.CompanyDto

object RemoteDataSource {

    private val api: BarnivoreApi = BarnivoreApiFactory.create()

    suspend fun getAutocompleteResults(query: String) = withContext(Dispatchers.IO) {
        api.autocomplete(query)
    }

    suspend fun getSearchResults(query: String): List<CompanyDto> = withContext(Dispatchers.IO) {
        api.search(query)
            .map { it.company }
    }

    suspend fun getCompanyDetails(id: Int): CompanyDto = withContext(Dispatchers.IO) {
        api.getCompany(id).company
    }
}
