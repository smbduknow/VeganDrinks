package me.smbduknow.vegandrinks.feature.search.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.smbduknow.vegandrinks.data.RemoteDataSource
import me.smbduknow.vegandrinks.data.model.CompanyDto
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

object SearchRepository {

    private val source = RemoteDataSource

    private val mapper = ProductMapper

    fun search(query: String): Flow<List<Product>> = flow {
        val searcResults = source.getSearchResults(query)
            .also { delay(100) }
            .map { company -> fetchCompanyProducts(company, query) }
            .flatten()
        emit(searcResults)
    }
        .flowOn(Dispatchers.IO)

    private suspend fun fetchCompanyProducts(companyDto: CompanyDto, query: String): List<Product> {
        val company = mapper.companyFromDto(companyDto)
        return source.getCompanyDetails(companyDto.id).products
            .filter { product -> product.product_name.contains(query, ignoreCase = true) }
            .map { productDto -> mapper.fromDto(productDto, company) }
    }

}
