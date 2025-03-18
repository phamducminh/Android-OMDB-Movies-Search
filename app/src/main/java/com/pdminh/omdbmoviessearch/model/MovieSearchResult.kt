package com.pdminh.omdbmoviessearch.model

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    @SerializedName("Response") var response: String = "",
    @SerializedName("Error") var error: String = "",
    @SerializedName("totalResults") var totalResults: String = "0",
    @SerializedName("Search") var search: ArrayList<Movie?>
)
