package com.pdminh.omdbmoviessearch.ui

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.pdminh.omdbmoviessearch.data.MovieSearchRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: MovieSearchRepository
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(MovieSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieSearchViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
