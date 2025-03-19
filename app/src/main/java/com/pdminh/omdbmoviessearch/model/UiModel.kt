package com.pdminh.omdbmoviessearch.model

sealed class UiModel {
    data class MovieItem(val movie: Movie?) : UiModel()
    data class EmptyItem(val errMessage: String) : UiModel()
    object LoadingItem : UiModel()
}