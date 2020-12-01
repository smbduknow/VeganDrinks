package me.smbduknow.vegandrinks.data.modelnew

data class CompanyDto(
    val id: Int,
    val company_name: String,
    val tag: String,
    val address: String,
    val city: String,
    val state: String,
    val postal: String,
    val country: String,
    val phone: String,
    val fax: String,
    val email: String,
    val url: String,
    val checked_by: String,
    val doubled_by: String,
    val notes: String,
    val created_on: String,
    val updated_on: String,
    val status: String,
    val red_yellow_green: String,
    val products: List<ProductDto> = emptyList()
) {

    class Wrapper(
        val company: CompanyDto
    )
}
