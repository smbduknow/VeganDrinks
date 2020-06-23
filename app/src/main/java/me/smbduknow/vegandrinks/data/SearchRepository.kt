package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.smbduknow.vegandrinks.data.model.Company
import me.smbduknow.vegandrinks.data.model.Product

class SearchRepository {

    private val source = RemoteDataSource

    fun search(query: String): Flow<List<Product>> = flow {
        val products = source.getSearchResults(query)
            .also { delay(100) }
            .map { company -> fetchCompanyProducts(company, query) }
            .flatten()
        emit(products)
    }


    private suspend fun fetchCompanyProducts(company: Company, query: String): List<Product> =
        source.getCompanyDetails(company.id).products
            .filter { it.product_name.contains(query, ignoreCase = true) }
            .onEach { it.company = company }
}
