package com.kevinserrano.apps.memoviesapp.domain.repository

import com.kevinserrano.apps.memoviesapp.common.Either
import com.kevinserrano.apps.memoviesapp.data.models.Movie

interface MoviesRepository {
    suspend fun getMovies(): Either<Throwable, List<Movie>>
}