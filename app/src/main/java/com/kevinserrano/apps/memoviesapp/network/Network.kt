package com.kevinserrano.apps.memoviesapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 20L

fun createNetworkClient(baseUrl: String, debug: Boolean = false): Retrofit =
    retrofitClient(baseUrl, httpClient(debug))

fun httpClient(debug: Boolean): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()

    if (debug) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder.run {
            addInterceptor(httpLoggingInterceptor)
        }
    }

    clientBuilder.run {
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS).build()
    }

    return clientBuilder.build()
}

fun retrofitClient(baseUrl: String, httpClient: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()