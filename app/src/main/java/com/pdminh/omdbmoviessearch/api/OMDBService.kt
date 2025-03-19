package com.pdminh.omdbmoviessearch.api

import com.pdminh.omdbmoviessearch.model.MovieSearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface OMDBService {

    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") pageIndex: Int,
    ): Response<MovieSearchResult>
}