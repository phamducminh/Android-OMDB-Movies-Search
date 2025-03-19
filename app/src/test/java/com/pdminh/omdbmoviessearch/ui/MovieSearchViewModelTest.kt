//package com.pdminh.omdbmoviessearch.ui
//
//import android.arch.core.executor.testing.InstantTaskExecutorRule
//import android.os.Looper
//import androidx.lifecycle.SavedStateHandle
//import com.pdminh.omdbmoviessearch.LiveDataTestUtil
//import com.pdminh.omdbmoviessearch.data.MovieSearchRepository
//import com.pdminh.omdbmoviessearch.model.Movie
//import com.pdminh.omdbmoviessearch.model.MovieSearchResult
//import com.pdminh.omdbmoviessearch.model.UiModel
//import com.pdminh.omdbmoviessearch.model.UiState
//import com.pdminh.omdbmoviessearch.util.ApiException
//import com.pdminh.omdbmoviessearch.util.AppConstants
//import com.pdminh.omdbmoviessearch.util.NoInternetException
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when` as whenever
//
//@ExperimentalCoroutinesApi
//class MovieSearchViewModelTest {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: MovieSearchViewModel
//    private val repository: MovieSearchRepository = mock()
//    private val savedStateHandle: SavedStateHandle = mock()
////    private val uiStateObserver: Observer<UiState<List<UiModel>>> = mock()
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    @Before
//    fun setUp() {
//        // Mock the MainLooper
//        val looper = mock(Looper::class.java)
//        whenever(Looper.getMainLooper()).thenReturn(looper)
//
//        // Set the main dispatcher to the test dispatcher
//        Dispatchers.setMain(testDispatcher)
//
//        // Mock the SavedStateHandle for the query string
//        whenever(savedStateHandle.get<String>(LAST_SEARCH_QUERY)).thenReturn("Inception")
//
//        // Create ViewModel
//        viewModel = MovieSearchViewModel(repository, savedStateHandle)
////        viewModel.uiStateLiveData.observeForever(uiStateObserver)
//    }
//
//    @Test
//    fun `test searchMovie - success`() = runTest(testDispatcher) {
//        val movie = Movie(
//            imdbID = "tt1375666",
//            title = "Inception",
//            poster = "https://example.com/inception.jpg",
//            year = "2010",
//            type = "movie"
//        )
//
//        val movieSearchResult = MovieSearchResult(
//            response = "True",
//            error = "",
//            totalResults = "1",
//            search = listOf(movie)
//        )
//
//        // Mock repository to return successful data
//        whenever(repository.getMovies("Inception", AppConstants.API_KEY, 1)).thenReturn(
//            movieSearchResult
//        )
//
//        // Call searchMovie
//        viewModel.searchMovie("Inception")
//
//        // Verify loading state
//        assertEquals(LiveDataTestUtil.getValue(viewModel.uiStateLiveData), UiState.Loading)
////        verify(uiStateObserver).onChanged(UiState.Loading)
//
//        // Verify success state with movie data
//        val expectedUiModel = UiModel.MovieItem(movie)
//        assertEquals(
//            LiveDataTestUtil.getValue(viewModel.uiStateLiveData),
//            UiState.Success(listOf(expectedUiModel))
//        )
////        verify(uiStateObserver).onChanged(UiState.Success(listOf(expectedUiModel)))
//    }
//
//    @Test
//    fun `test searchMovie - error ApiException`() = runTest(testDispatcher) {
//        // Mock repository to throw ApiException
//        whenever(repository.getMovies("Inception", AppConstants.API_KEY, 1))
//            .thenThrow(ApiException("API Error"))
//
//        // Call searchMovie
//        viewModel.searchMovie("Inception")
//
//        // Verify loading state
//        assertEquals(LiveDataTestUtil.getValue(viewModel.uiStateLiveData), UiState.Loading)
////        verify(uiStateObserver).onChanged(UiState.Loading)
//
//        // Verify error state
//        assertEquals(
//            LiveDataTestUtil.getValue(viewModel.uiStateLiveData),
//            UiState.Error("API Error")
//        )
////        verify(uiStateObserver).onChanged(UiState.Error("API Error"))
//    }
//
//    @Test
//    fun `test searchMovie - error NoInternetException`() = runTest(testDispatcher) {
//        // Mock repository to throw NoInternetException
//        whenever(repository.getMovies("Inception", AppConstants.API_KEY, 1)).thenThrow(
//            NoInternetException("No internet")
//        )
//
//        // Call searchMovie
//        viewModel.searchMovie("Inception")
//
//        // Verify loading state
//        assertEquals(LiveDataTestUtil.getValue(viewModel.uiStateLiveData), UiState.Loading)
////        verify(uiStateObserver).onChanged(UiState.Loading)
//
//        // Verify error state
//        assertEquals(
//            LiveDataTestUtil.getValue(viewModel.uiStateLiveData),
//            UiState.Error("No internet")
//        )
////        verify(uiStateObserver).onChanged(UiState.Error("No internet"))
//    }
//
//    @Test
//    fun `test loadMore - success`() = runTest(testDispatcher) {
//        val movie = Movie(
//            imdbID = "tt1375666",
//            title = "Inception",
//            poster = "https://example.com/inception.jpg",
//            year = "2010",
//            type = "movie"
//        )
//
//        val movieSearchResult = MovieSearchResult(
//            response = "True",
//            error = "",
//            totalResults = "100",
//            search = listOf(movie)
//        )
//
//        // Mock repository to return successful data
//        whenever(repository.getMovies("Inception", AppConstants.API_KEY, 2)).thenReturn(
//            movieSearchResult
//        )
//
//        // Call loadMore
//        viewModel.loadMore(1000)
//
//        // Verify loading state
//        assertEquals(LiveDataTestUtil.getValue(viewModel.uiStateLiveData), UiState.Loading)
////        verify(uiStateObserver).onChanged(UiState.Loading)
//
//        // Verify success state with movie data
//        val expectedUiModel = UiModel.MovieItem(movie)
//
//        assertEquals(
//            LiveDataTestUtil.getValue(viewModel.uiStateLiveData),
//            UiState.Success(listOf(expectedUiModel))
//        )
////        verify(uiStateObserver).onChanged(UiState.Success(listOf(expectedUiModel)))
//    }
//
//    @Test
//    fun `test checkForLoadMoreItems - should trigger load more`() {
//        // Mock the LiveData values for visibleItemCount, totalItemCount, and firstVisibleItemPosition
//        val visibleItemCount = 10
//        val totalItemCount = 30
//        val firstVisibleItemPosition = 20
//
//        // Trigger the method to check for load more
//        viewModel.checkForLoadMoreItems(visibleItemCount, totalItemCount, firstVisibleItemPosition)
//
//        // Verify load more flag is set to true
//        assertEquals(LiveDataTestUtil.getValue(viewModel.loadMoreListLiveData), UiState.Loading)
////        verify(uiStateObserver).onChanged(UiState.Loading)
//    }
//
////    @Test
////    fun `test onCleared - saved query`() {
////        // Call onCleared
////        viewModel.onCleared()
////
////        // Verify that savedStateHandle.put() is called with the last query
////        verify(savedStateHandle).set<String>(LAST_SEARCH_QUERY, "Inception")
////    }
//}