package com.pdminh.omdbmoviessearch.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UiModelTest {

    @Test
    fun `MovieItem should store movie data correctly`() {
        val movie = Movie("Inception", "2010", "Nolan", "Sci-Fi")
        val movieItem = UiModel.MovieItem(movie)

        assertThat(movieItem.movie).isEqualTo(movie)
    }

    @Test
    fun `EmptyItem should store error message correctly`() {
        val errorMessage = "No movies found"
        val emptyItem = UiModel.EmptyItem(errorMessage)

        assertThat(emptyItem.errMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `LoadingItem should be a singleton instance`() {
        val firstInstance = UiModel.LoadingItem
        val secondInstance = UiModel.LoadingItem

        assertThat(firstInstance).isSameInstanceAs(secondInstance)
    }
}