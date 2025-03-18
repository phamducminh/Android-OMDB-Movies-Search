package com.pdminh.omdbmoviessearch.model

import com.google.gson.annotations.SerializedName

data class MovieItem(
    @SerializedName("Type") var type: String = "",
    @SerializedName("Year") var year: String = "",
    @SerializedName("imdbID") var imdbID: String = "",
    @SerializedName("Poster") var poster: String = "",
    @SerializedName("Title") var title: String = "",
)
