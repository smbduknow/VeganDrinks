package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.flow.*
import me.smbduknow.vegandrinks.data.model.Product

class SearchRepository {

    private val source = RemoteDataSource

    fun search(query: String): Flow<List<Product>> =
        flow { emit(source.getSearchResults(query)) }
            .flatMapLatest { companies -> companies.asFlow() }
            .map { company -> source.getCompanyDetails(company.id) }
            .map { company ->
                company.products
                    .filter { it.product_name.contains(query, ignoreCase = true) }
                    .onEach { it.company = company }
            }
}
