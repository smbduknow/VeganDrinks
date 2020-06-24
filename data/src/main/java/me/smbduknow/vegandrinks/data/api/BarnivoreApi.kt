package me.smbduknow.vegandrinks.data.api

import me.smbduknow.vegandrinks.data.model.CompanyDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BarnivoreApi {

    @GET("autocomplete.json")
    suspend fun autocomplete(
        @Query("term") query: String
    ): List<String>

    @GET("search.json")
    suspend fun search(
        @Query("keyword") query: String
    ): List<CompanyDto.Wrapper>

    @GET("company/{id}.json")
    suspend fun getCompany(
        @Path("id") id: Int
    ): CompanyDto.Wrapper
}
