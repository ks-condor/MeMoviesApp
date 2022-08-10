package com.kevinserrano.apps.memoviesapp.domain.usecase

import com.kevinserrano.apps.memoviesapp.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend fun run() = moviesRepository.getMovies()
}