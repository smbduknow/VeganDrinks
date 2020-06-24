package me.smbduknow.vegandrinks.data

import me.smbduknow.vegandrinks.data.model.Company
import me.smbduknow.vegandrinks.data.model.CompanyDto
import me.smbduknow.vegandrinks.data.model.Product
import me.smbduknow.vegandrinks.data.model.ProductDto

object ProductMapper {

    fun fromDto(productDto: ProductDto): Product = with(productDto) {
        Product(
            id = id,
            company_id = company_id,
            product_name = product_name,
            status = status,
            country = country,
            booze_type = booze_type,
            red_yellow_green = red_yellow_green
        )
    }

    fun companyFromDto(companyDto: CompanyDto) = with(companyDto) {
        Company(
            id = id,
            company_name = company_name,
            notes = notes
        )
    }
}
