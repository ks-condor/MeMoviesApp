package com.kevinserrano.apps.memoviesapp.domain.datasource

import com.kevinserrano.apps.memoviesapp.data.models.Movie

interface MovieRemoteDataSource {
    suspend fun getMovies(): List<Movie>
}