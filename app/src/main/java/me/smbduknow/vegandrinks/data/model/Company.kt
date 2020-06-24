package me.smbduknow.vegandrinks.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    val id: Int,
    val company_name: String,
    val notes: String
) : Parcelable
