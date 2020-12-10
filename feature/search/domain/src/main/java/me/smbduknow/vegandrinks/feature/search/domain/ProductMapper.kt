package me.smbduknow.vegandrinks.feature.search.domain

import me.smbduknow.vegandrinks.data.model.CompanyDto
import me.smbduknow.vegandrinks.data.model.ProductDto
import me.smbduknow.vegandrinks.feature.search.domain.model.Company
import me.smbduknow.vegandrinks.feature.search.domain.model.Product
import java.util.*

internal object ProductMapper {

    fun fromDto(productDto: ProductDto, company: Company): Product = with(productDto) {
        Product(
            id = id,
            name = product_name,
            status = resolveStatus(red_yellow_green),
            type = resolveType(booze_type),
            country = country,
            company = company
        )
    }

    fun companyFromDto(companyDto: CompanyDto) = with(companyDto) {
        Company(
            id = id,
            name = company_name,
            notes = notes
        )
    }

    private fun resolveType(type: String) =
        when (type.toLowerCase(Locale.getDefault())) {
            TYPE_BEER -> Product.Type.BEER
            TYPE_WINE -> Product.Type.WINE
            TYPE_LIQUOR -> Product.Type.LIQUOR
            else -> Product.Type.OTHER
        }

    private fun resolveStatus(status: String) =
        when (status.toLowerCase(Locale.getDefault())) {
            STATUS_VEGAN -> Product.Status.VEGAN
            STATUS_NOT_VEGAN -> Product.Status.NOT_VEGAN
            else -> Product.Status.UNKNOWN
        }

    private const val TYPE_BEER = "beer"
    private const val TYPE_WINE = "wine"
    private const val TYPE_LIQUOR = "liquor"

    private const val STATUS_VEGAN = "green"
    private const val STATUS_NOT_VEGAN = "red"
}
