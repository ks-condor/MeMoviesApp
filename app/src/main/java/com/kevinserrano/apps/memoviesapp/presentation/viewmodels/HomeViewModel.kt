package com.kevinserrano.apps.memoviesapp.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.kevinserrano.apps.memoviesapp.data.models.Movie
import com.kevinserrano.apps.memoviesapp.domain.usecase.GetMoviesUseCase
import com.kevinserrano.apps.memoviesapp.utilities.AbstractViewModel
import com.kevinserrano.apps.memoviesapp.utilities.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    coroutineContextProvider: CoroutineContextProvider
) : AbstractViewModel<HomeViewModel.State, HomeViewModel.Event>(
    initialState = State(),
    coroutineContextProvider = coroutineContextProvider
) {

    init {
        fetchMovies()
    }

    fun tryAgain() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    loading = true,
                    error = null, movies = null
                )
            }
        }
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            getMoviesUseCase.run().either(
                ::handleGetMoviesFailure,
                ::handleGetMoviesSuccess
            )
        }
    }

    private fun handleGetMoviesFailure(failure: Throwable) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    loading = false,
                    error = failure.localizedMessage ?: "error", movies = null
                )
            }
        }
    }

    private fun handleGetMoviesSuccess(movies: List<Movie>) {
        viewModelScope.launch {
            updateState {
                it.copy(loading = false, movies = movies, error = null)
            }
        }
    }

    fun onClickMovie(movie: Movie) {
        viewModelScope.launch {
            _event.emit(Event.NavigateToDetail(movie = movie))
            delay(100)
            _event.emit(Event.NONE)
        }
    }

    data class State(
        val loading: Boolean = true,
        val movies: List<Movie>? = null,
        val error: String? = null
    )

    sealed interface Event {
        object NONE : Event
        data class NavigateToDetail(val movie: Movie) : Event
    }
}