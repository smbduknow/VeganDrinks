package me.smbduknow.vegandrinks.data

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSource {

    private const val API_URL = "http://barnivore.com/"

    private val API: BarnivoreApi

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        API = retrofit.create(BarnivoreApi::class.java)
    }

    fun getSearchResults(query: String) = API.search(query)
        .subscribeOn(Schedulers.io())
        .map { list -> list.map { it.company } }

    fun getCompanyDetails(id: Int) = API.getCompany(id)
        .subscribeOn(Schedulers.io())
        .map { it.company  }
}