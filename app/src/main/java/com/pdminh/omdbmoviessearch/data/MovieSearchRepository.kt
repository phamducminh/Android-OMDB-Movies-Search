package com.pdminh.omdbmoviessearch.data

import com.pdminh.omdbmoviessearch.api.OMDBService
import com.pdminh.omdbmoviessearch.api.SafeApiRequest
import com.pdminh.omdbmoviessearch.model.MovieSearchResult

class MovieSearchRepository(
    private val service: OMDBService
) : SafeApiRequest() {

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): MovieSearchResult {

        return apiRequest { service.getSearchResultData(searchTitle, apiKey, pageIndex) }
    }
}