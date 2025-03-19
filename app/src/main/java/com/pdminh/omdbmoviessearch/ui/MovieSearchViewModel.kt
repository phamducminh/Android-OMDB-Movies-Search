package com.pdminh.omdbmoviessearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdminh.omdbmoviessearch.data.MovieSearchRepository
import com.pdminh.omdbmoviessearch.model.Movie
import com.pdminh.omdbmoviessearch.model.MovieSearchResult
import com.pdminh.omdbmoviessearch.model.UiModel
import com.pdminh.omdbmoviessearch.model.UiState
import com.pdminh.omdbmoviessearch.util.ApiException
import com.pdminh.omdbmoviessearch.util.AppConstants
import com.pdminh.omdbmoviessearch.util.NoInternetException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for the [MovieSearchActivity] screen.
 * The ViewModel works with the [MovieSearchRepository] to get the data.
 */
@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val repository: MovieSearchRepository,
    /*private val savedStateHandle: SavedStateHandle*/
) : ViewModel() {

    private var queryString = ""
    private var pageIndex = 0
    private var totalMovies = 0
    private var movies = mutableListOf<Movie?>()

    private val _uiStateLiveData = MutableLiveData<UiState<List<UiModel>>>()
    val uiStateLiveData: LiveData<UiState<List<UiModel>>>
        get() = _uiStateLiveData

    private val _loadMoreListLiveData = MutableLiveData<Boolean>()
    val loadMoreListLiveData: LiveData<Boolean>
        get() = _loadMoreListLiveData

    private lateinit var movieResponse: MovieSearchResult

    init {
        _loadMoreListLiveData.value = false
    }

    fun getMovies() {
        if (pageIndex == 1) {
            movies.clear()
            _uiStateLiveData.postValue(UiState.Loading)
        } else {
            if (movies.isNotEmpty() && movies.last() == null)
                movies.removeAt(movies.size - 1)
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (queryString.isNotEmpty()) {
                try {
                    movieResponse = repository.getMovies(
                        queryString,
                        AppConstants.API_KEY,
                        pageIndex
                    )
                    withContext(Dispatchers.Main) {
                        if (movieResponse.response == AppConstants.SUCCESS) {
                            movies.addAll(movieResponse.search)
                            totalMovies = movieResponse.totalResults.toInt()
                            _uiStateLiveData.postValue(UiState.Success(movies.map {
                                UiModel.MovieItem(
                                    it
                                )
                            }))
                            _loadMoreListLiveData.value = false
                        } else
                            _uiStateLiveData.postValue(UiState.Error(movieResponse.error))
                    }
                } catch (e: ApiException) {
                    withContext(Dispatchers.Main) {
                        _uiStateLiveData.postValue(UiState.Error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                } catch (e: NoInternetException) {
                    withContext(Dispatchers.Main) {
                        _uiStateLiveData.postValue(UiState.Error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }
        }
    }

    fun searchMovie(query: String) {
        queryString = query
        pageIndex = 1
        totalMovies = 0
        getMovies()
    }

    fun loadMore(delay: Long = 0) {
        viewModelScope.launch {
            delay(delay)
            pageIndex++
            getMovies()
        }
    }

    fun checkForLoadMoreItems(
        visibleItemCount: Int,
        totalItemCount: Int,
        firstVisibleItemPosition: Int
    ) {
        if (!_loadMoreListLiveData.value!! && (totalItemCount < totalMovies)) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                _loadMoreListLiveData.value = true
            }
        }
    }
}