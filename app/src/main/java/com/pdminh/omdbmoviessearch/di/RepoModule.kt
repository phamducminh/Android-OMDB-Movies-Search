package com.pdminh.omdbmoviessearch.di

import com.pdminh.omdbmoviessearch.api.OMDBService
import com.pdminh.omdbmoviessearch.data.MovieSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideMovieSearchRepository(service: OMDBService): MovieSearchRepository {
        return MovieSearchRepository(service)
    }
}