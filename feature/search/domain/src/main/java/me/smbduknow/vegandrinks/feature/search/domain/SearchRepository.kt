package me.smbduknow.vegandrinks.feature.search.domain

import kotlinx.coroutines.delay
import me.smbduknow.vegandrinks.data.RemoteDataSource
import me.smbduknow.vegandrinks.data.model.CompanyDto
import me.smbduknow.vegandrinks.feature.search.domain.model.Product

object SearchRepository {

    private val source = RemoteDataSource

    private val mapper = ProductMapper

    suspend fun search(query: String): List<Product> =
         source.getSearchResults(query)
            .also { delay(100) }
            .map { company -> fetchCompanyProducts(company, query) }
            .flatten()

    private suspend fun fetchCompanyProducts(companyDto: CompanyDto, query: String): List<Product> {
        val company = mapper.companyFromDto(companyDto)
        return source.getCompanyDetails(companyDto.id).products
            .filter { product -> product.product_name.contains(query, ignoreCase = true) }
            .map { productDto -> mapper.fromDto(productDto, company) }
    }

}
