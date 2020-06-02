package me.smbduknow.vegandrinks.data

import me.smbduknow.vegandrinks.data.model.Company
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BarnivoreApi {

    @GET("autocomplete.json")
    suspend fun autocomplete(
        @Query("term") query: String
    ): List<String>

    @GET("search.json")
    suspend fun search(
        @Query("keyword") query: String
    ): List<Company.Wrapper>

    @GET("company/{id}.json")
    suspend fun getCompany(
        @Path("id") id: Int
    ): Company.Wrapper
}
