package com.pdminh.omdbmoviessearch.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.JsonElement
import org.junit.Test

class MovieSearchResultTest {

    private val gson = Gson()

    @Test
    fun `default values should be correct`() {
        val movieSearchResult = MovieSearchResult()

        assertThat(movieSearchResult.response).isEqualTo("")
        assertThat(movieSearchResult.error).isEqualTo("")
        assertThat(movieSearchResult.totalResults).isEqualTo("0")
        assertThat(movieSearchResult.search).isEmpty()
    }

    @Test
    fun `should correctly assign values via constructor`() {
        val movie = Movie(
            title = "Inception",
            year = "2010",
            imdbID = "tt1375666",
            poster = "https://example.com/inception.jpg"
        )
        val movieSearchResult = MovieSearchResult(
            response = "True",
            error = "",
            totalResults = "100",
            search = listOf(movie)
        )

        assertThat(movieSearchResult.response).isEqualTo("True")
        assertThat(movieSearchResult.error).isEmpty()
        assertThat(movieSearchResult.totalResults).isEqualTo("100")
        assertThat(movieSearchResult.search).hasSize(1)
        assertThat(movieSearchResult.search[0]?.title).isEqualTo("Inception")
    }

    @Test
    fun `should correctly serialize to JSON`() {
        val movie = Movie(
            title = "Inception",
            year = "2010",
            imdbID = "tt1375666",
            poster = "https://example.com/inception.jpg"
        )
        val movieSearchResult = MovieSearchResult(
            response = "True",
            error = "",
            totalResults = "100",
            search = listOf(movie)
        )

        val json = gson.toJson(movieSearchResult)

        val expectedJson = """
        {
            "Response": "True",
            "Error": "",
            "totalResults": "100",
            "Search": [
                {
                    "Title": "Inception",
                    "Year": "2010",
                    "imdbID": "tt1375666",
                    "Poster": "https://example.com/inception.jpg",
                    "Type":""
                }
            ]
        }
    """.trimIndent()

        val jsonElement = gson.fromJson(json, JsonElement::class.java)
        val expectedJsonElement = gson.fromJson(expectedJson, JsonElement::class.java)

        assertThat(jsonElement).isEqualTo(expectedJsonElement)
    }

    @Test
    fun `should correctly deserialize from JSON`() {
        val json = """
            {
                "Response": "True",
                "Error": "",
                "totalResults": "100",
                "Search": [
                    {
                        "imdbID": "tt1375666",
                        "Title": "Inception",
                        "Year": "2010",
                        "Poster": "https://example.com/inception.jpg"
                    }
                ]
            }
        """.trimIndent()

        val result = gson.fromJson(json, MovieSearchResult::class.java)

        assertThat(result.response).isEqualTo("True")
        assertThat(result.error).isEmpty()
        assertThat(result.totalResults).isEqualTo("100")
        assertThat(result.search).hasSize(1)
        assertThat(result.search[0]?.title).isEqualTo("Inception")
    }

    @Test
    fun `should handle empty search list correctly`() {
        val json = """
            {
                "Response": "True",
                "Error": "",
                "totalResults": "0",
                "Search": []
            }
        """.trimIndent()

        val result = gson.fromJson(json, MovieSearchResult::class.java)

        assertThat(result.response).isEqualTo("True")
        assertThat(result.error).isEmpty()
        assertThat(result.totalResults).isEqualTo("0")
        assertThat(result.search).isEmpty()
    }

    @Test
    fun `should handle missing fields in JSON`() {
        val json = """
            {
                "Response": "False",
                "Error": "Movie not found!"
            }
        """.trimIndent()

        val result = gson.fromJson(json, MovieSearchResult::class.java)

        assertThat(result.response).isEqualTo("False")
        assertThat(result.error).isEqualTo("Movie not found!")
        assertThat(result.totalResults).isEqualTo("0") // Default value
        assertThat(result.search).isEmpty() // Default value
    }
}