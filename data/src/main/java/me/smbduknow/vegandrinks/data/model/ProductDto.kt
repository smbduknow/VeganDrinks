package me.smbduknow.vegandrinks.data.model

data class ProductDto(
    val id: Int,
    val company_id: Int,
    val product_name: String,
    val status: String,
    val created_on: String,
    val updated_on: String,
    val address: String,
    val city: String,
    val state: String,
    val postal: String,
    val country: String,
    val phone: String,
    val fax: String,
    val email: String,
    val url: String,
    val same_contact_as_company: Boolean,
    val booze_type: String,
    val red_yellow_green: String
)
