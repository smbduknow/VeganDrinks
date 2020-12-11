package me.smbduknow.vegandrinks.feature.search.domain.model

import java.io.Serializable

data class Company(
    val id: Int,
    val name: String,
    val notes: String
): Serializable
