package com.kevinserrano.apps.memoviesapp.data.remote

import com.kevinserrano.apps.memoviesapp.data.models.MovieDBResponse
import retrofit2.http.*

interface PelisVideosLiteApi {

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key") key: String = "c2ba1acc67fd32e67b5d1dd2ac8b53f1",
        @Query("language") language: String = "es-US",
        @Query("page") page: Int = 1
    ): MovieDBResponse
}