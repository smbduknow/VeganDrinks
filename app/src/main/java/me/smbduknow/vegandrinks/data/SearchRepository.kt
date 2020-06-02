package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import me.smbduknow.vegandrinks.data.model.Product

class SearchRepository {

    private val source = RemoteDataSource

    suspend fun search(query: String): List<Product> =
        source.getSearchResults(query)
            .map {
                GlobalScope.async {
                    source.getCompanyDetails(it.id)
                }.await()
            }
            .flatMap { company ->
                company.products
                    .filter { it.product_name.contains(query, ignoreCase = true) }
                    .onEach { it.company = company }
            }
}
