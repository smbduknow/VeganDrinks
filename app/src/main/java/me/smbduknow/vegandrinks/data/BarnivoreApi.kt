package me.smbduknow.vegandrinks.data

import io.reactivex.Single
import me.smbduknow.vegandrinks.data.model.Company
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BarnivoreApi {

    @GET("autocomplete.json")
    fun autocomplete(
        @Query("term") query: String
    ): Single<List<String>>

    @GET("search.json")
    fun search(
        @Query("keyword") query: String
    ): Single<List<Company.Wrapper>>

    @GET("company/{id}.json")
    fun getCompany(
        @Path("id") id: Int
    ): Single<Company.Wrapper>
}