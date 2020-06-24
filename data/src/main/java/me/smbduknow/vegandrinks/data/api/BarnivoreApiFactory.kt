package me.smbduknow.vegandrinks.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal object BarnivoreApiFactory {

    private const val API_URL = "http://barnivore.com/"

    fun create(): BarnivoreApi = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(httpClient)
        .addConverterFactory(jsonConverterFactory)
        .build()
        .create()

    private val loggingInterceptor: Interceptor
        get() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    private val httpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    private val jsonConverterFactory: Converter.Factory
        get() = GsonConverterFactory.create()

}
