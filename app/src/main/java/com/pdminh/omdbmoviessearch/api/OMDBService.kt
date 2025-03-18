package com.pdminh.omdbmoviessearch.api

import com.pdminh.omdbmoviessearch.model.MovieSearchResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OMDBService {

    @GET("?type=movie")
    suspend fun getSearchResultData(
        @Query(value = "s") searchTitle: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") pageIndex: Int,
    ): Response<MovieSearchResult>

    companion object {
        private const val BASE_URL = "https://www.omdbapi.com/"

        fun create(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): OMDBService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(logger)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OMDBService::class.java)
        }
    }
}