package com.pdminh.omdbmoviessearch.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("imdbID") var imdbID: String = "",
    @SerializedName("Title") var title: String = "",
    @SerializedName("Poster") var poster: String = "",
    @SerializedName("Year") var year: String = "",
    @SerializedName("Type") var type: String = "",
)
