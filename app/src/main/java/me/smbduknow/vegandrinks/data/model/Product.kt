package me.smbduknow.vegandrinks.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val company_id: Int,
    val product_name: String,
    val status: String,
    val country: String,
    val booze_type: String,
    val red_yellow_green: String,
    var company: Company? = null
) : Parcelable
