package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.smbduknow.vegandrinks.data.model.CompanyDto
import me.smbduknow.vegandrinks.data.model.Product

class SearchRepository {

    private val source = RemoteDataSource

    private val mapper = ProductMapper

    fun search(query: String): Flow<List<Product>> = flow {
        val products = source.getSearchResults(query)
            .also { delay(100) }
            .map { company -> fetchCompanyProducts(company, query) }
            .flatten()
        emit(products)
    }


    private suspend fun fetchCompanyProducts(companyDto: CompanyDto, query: String): List<Product> {
        val company = mapper.companyFromDto(companyDto)
        return source.getCompanyDetails(companyDto.id).products
            .filter { it.product_name.contains(query, ignoreCase = true) }
            .map { mapper.fromDto(it) }
            .onEach { it.company = company }
    }

}
