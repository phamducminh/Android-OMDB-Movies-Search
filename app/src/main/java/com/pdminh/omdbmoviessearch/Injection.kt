package com.pdminh.omdbmoviessearch

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.pdminh.omdbmoviessearch.api.NetworkConnectionInterceptor
import com.pdminh.omdbmoviessearch.api.OMDBService
import com.pdminh.omdbmoviessearch.data.MovieSearchRepository
import com.pdminh.omdbmoviessearch.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [NetworkConnectionInterceptor]
     */
    private fun provideNetworkConnectionInterceptor(context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    /**
     * Creates an instance of [MovieSearchRepository] based on the [OMDBService]
     */
    private fun provideMovieSearchRepository(context: Context): MovieSearchRepository {
        return MovieSearchRepository(
            OMDBService.create(provideNetworkConnectionInterceptor(context))
        )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(
        context: Context,
        owner: SavedStateRegistryOwner
    ): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideMovieSearchRepository(context))
    }
}