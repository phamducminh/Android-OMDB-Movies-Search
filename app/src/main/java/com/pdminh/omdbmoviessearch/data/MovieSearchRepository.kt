package com.pdminh.omdbmoviessearch.data

import com.pdminh.omdbmoviessearch.api.OMDBService
import com.pdminh.omdbmoviessearch.api.SafeApiRequest
import com.pdminh.omdbmoviessearch.model.MovieSearchResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieSearchRepository @Inject constructor(
    private val service: OMDBService
) : SafeApiRequest() {

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): MovieSearchResult =
        apiRequest { service.getSearchResultData(searchTitle, apiKey, pageIndex) }
}