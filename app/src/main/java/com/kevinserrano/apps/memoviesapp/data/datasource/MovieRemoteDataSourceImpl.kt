package com.kevinserrano.apps.memoviesapp.data.datasource

import com.kevinserrano.apps.memoviesapp.data.models.Movie
import com.kevinserrano.apps.memoviesapp.data.remote.PelisVideosLiteApi
import com.kevinserrano.apps.memoviesapp.domain.datasource.MovieRemoteDataSource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val api:PelisVideosLiteApi): MovieRemoteDataSource {

    override suspend fun getMovies(): List<Movie> {
        return api.getMoviesPopular().movies.map { it.toMovie() }
    }
}