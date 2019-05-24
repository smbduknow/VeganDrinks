package me.smbduknow.vegandrinks.data.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
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
    val red_yellow_green: String
) : Parcelable {

    @IgnoredOnParcel
    var products: List<Product> = emptyList()

    class Wrapper(
        val company: Company
    )
}