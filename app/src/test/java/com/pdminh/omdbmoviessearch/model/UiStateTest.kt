package com.pdminh.omdbmoviessearch.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UiStateTest {

    @Test
    fun `Success should store data correctly`() {
        val data = "Success Data"
        val successState = UiState.Success(data)

        assertThat(successState.data).isEqualTo(data)
    }

    @Test
    fun `Error should store message correctly`() {
        val errorMessage = "An error occurred"
        val errorState = UiState.Error(errorMessage)

        assertThat(errorState.message).isEqualTo(errorMessage)
    }

    @Test
    fun `Loading should be a singleton instance`() {
        val firstInstance = UiState.Loading
        val secondInstance = UiState.Loading

        assertThat(firstInstance).isSameInstanceAs(secondInstance)
    }
}