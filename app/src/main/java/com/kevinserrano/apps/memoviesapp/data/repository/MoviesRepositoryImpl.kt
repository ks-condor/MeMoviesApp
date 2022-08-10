package com.kevinserrano.apps.memoviesapp.data.repository

import com.kevinserrano.apps.memoviesapp.common.Either
import com.kevinserrano.apps.memoviesapp.data.models.Movie
import com.kevinserrano.apps.memoviesapp.domain.datasource.MovieRemoteDataSource
import com.kevinserrano.apps.memoviesapp.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MoviesRepository {

    override suspend fun getMovies(): Either<Throwable, List<Movie>> = try {
        Either.Right(movieRemoteDataSource.getMovies())
    } catch (ex: Exception) {
        Either.Left(ex)
    }
}