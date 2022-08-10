package com.kevinserrano.apps.memoviesapp.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.kevinserrano.apps.memoviesapp.utilities.AbstractViewModel
import com.kevinserrano.apps.memoviesapp.utilities.CoroutineContextProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider
) : AbstractViewModel<DetailViewModel.State, DetailViewModel.Event>(
    initialState = State(),
    coroutineContextProvider = coroutineContextProvider
) {

    fun playMovie(movieURL: String) {
        viewModelScope.launch {
            _event.emit(Event.PlayMovie(movieURL))
        }
    }

    data class State(
        val loading: Boolean = true,
        val photos: List<String>? = null,
        val error: String? = null
    )

    sealed interface Event {
        object NONE : Event
        data class PlayMovie(val movieURL: String) : Event
    }
}