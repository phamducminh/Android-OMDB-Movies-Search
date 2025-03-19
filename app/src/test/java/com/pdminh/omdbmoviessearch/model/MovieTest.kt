package com.pdminh.omdbmoviessearch.model

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.junit.Test

class MovieTest {

    private val gson = Gson()

    @Test
    fun `should create Movie with default values`() {
        val movie = Movie()

        assertThat(movie.imdbID).isEqualTo("")
        assertThat(movie.title).isEqualTo("")
        assertThat(movie.poster).isEqualTo("")
        assertThat(movie.year).isEqualTo("")
        assertThat(movie.type).isEqualTo("")
    }

    @Test
    fun `should correctly assign values via constructor`() {
        val movie = Movie(
            imdbID = "tt1375666",
            title = "Inception",
            poster = "https://example.com/inception.jpg",
            year = "2010",
            type = "movie"
        )

        assertThat(movie.imdbID).isEqualTo("tt1375666")
        assertThat(movie.title).isEqualTo("Inception")
        assertThat(movie.poster).isEqualTo("https://example.com/inception.jpg")
        assertThat(movie.year).isEqualTo("2010")
        assertThat(movie.type).isEqualTo("movie")
    }

    @Test
    fun `should correctly serialize to JSON`() {
        val movie = Movie(
            imdbID = "tt1375666",
            title = "Inception",
            poster = "https://example.com/inception.jpg",
            year = "2010",
            type = "movie"
        )

        val json = gson.toJson(movie)

        val expectedJson = """
            {
                "imdbID": "tt1375666",
                "Title": "Inception",
                "Poster": "https://example.com/inception.jpg",
                "Year": "2010",
                "Type": "movie"
            }
        """.trimIndent().replace("\\s".toRegex(), "")

        assertThat(json.replace("\\s".toRegex(), "")).isEqualTo(expectedJson)
    }

    @Test
    fun `should correctly deserialize from JSON`() {
        val json = """
            {
                "imdbID": "tt1375666",
                "Title": "Inception",
                "Poster": "https://example.com/inception.jpg",
                "Year": "2010",
                "Type": "movie"
            }
        """.trimIndent()

        val movie = gson.fromJson(json, Movie::class.java)

        assertThat(movie.imdbID).isEqualTo("tt1375666")
        assertThat(movie.title).isEqualTo("Inception")
        assertThat(movie.poster).isEqualTo("https://example.com/inception.jpg")
        assertThat(movie.year).isEqualTo("2010")
        assertThat(movie.type).isEqualTo("movie")
    }

    @Test
    fun `should handle missing fields in JSON`() {
        val json = """
            {
                "imdbID": "tt1375666",
                "Title": "Inception"
            }
        """.trimIndent()

        val movie = gson.fromJson(json, Movie::class.java)

        assertThat(movie.imdbID).isEqualTo("tt1375666")
        assertThat(movie.title).isEqualTo("Inception")
        assertThat(movie.poster).isEqualTo("")
        assertThat(movie.year).isEqualTo("")
        assertThat(movie.type).isEqualTo("")
    }

    @Test
    fun `should handle empty JSON`() {
        val json = "{}"

        val movie = gson.fromJson(json, Movie::class.java)

        assertThat(movie.imdbID).isEqualTo("")
        assertThat(movie.title).isEqualTo("")
        assertThat(movie.poster).isEqualTo("")
        assertThat(movie.year).isEqualTo("")
        assertThat(movie.type).isEqualTo("")
    }

    @Test
    fun `should correctly update values`() {
        val movie = Movie()

        movie.imdbID = "tt4154796"
        movie.title = "Avengers: Endgame"
        movie.poster = "https://example.com/endgame.jpg"
        movie.year = "2019"
        movie.type = "movie"

        assertThat(movie.imdbID).isEqualTo("tt4154796")
        assertThat(movie.title).isEqualTo("Avengers: Endgame")
        assertThat(movie.poster).isEqualTo("https://example.com/endgame.jpg")
        assertThat(movie.year).isEqualTo("2019")
        assertThat(movie.type).isEqualTo("movie")
    }
}