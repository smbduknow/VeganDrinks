package me.smbduknow.vegandrinks.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.smbduknow.vegandrinks.Application
import me.smbduknow.vegandrinks.data.network.ConnectivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RemoteDataSource {

    private const val API_URL = "http://barnivore.com/"

    private val API: BarnivoreApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(Application.instance))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        API = retrofit.create()
    }

    suspend fun getAutocompleteResults(query: String) = withContext(Dispatchers.IO) {
        API.autocomplete(query)
    }

    suspend fun getSearchResults(query: String) = withContext(Dispatchers.IO) {
        API.search(query)
            .map { it.company }
    }

    suspend fun getCompanyDetails(id: Int) = withContext(Dispatchers.IO) {
        API.getCompany(id).company
    }
}
