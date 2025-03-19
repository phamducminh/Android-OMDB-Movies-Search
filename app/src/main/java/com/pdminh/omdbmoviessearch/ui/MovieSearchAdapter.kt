package com.pdminh.omdbmoviessearch.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.model.UiModel
import com.pdminh.omdbmoviessearch.ui.viewholder.EmptyViewHolder
import com.pdminh.omdbmoviessearch.ui.viewholder.LoadingViewHolder
import com.pdminh.omdbmoviessearch.ui.viewholder.MovieViewHolder
import javax.inject.Inject

/**
 * Adapter for the list of repositories.
 */
class MovieSearchAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = mutableListOf<UiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.movie_view_item -> MovieViewHolder.create(parent)
            R.layout.loading_view_item -> LoadingViewHolder.create(parent)
            R.layout.empty_view_item -> EmptyViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = movies[position]
        when (uiModel) {
            is UiModel.MovieItem -> uiModel.movie?.let { (holder as MovieViewHolder).bind(it) }
            is UiModel.LoadingItem -> (holder as LoadingViewHolder).bind()
            is UiModel.EmptyItem -> (holder as EmptyViewHolder).bind(uiModel.errMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (movies[position]) {
            is UiModel.MovieItem -> R.layout.movie_view_item
            is UiModel.LoadingItem -> R.layout.loading_view_item
            is UiModel.EmptyItem -> R.layout.empty_view_item
        }
    }

    fun setData(newMovies: List<UiModel>) {
        if (newMovies.size == 1 && newMovies[0] is UiModel.LoadingItem) {
            movies.add(newMovies[0])
        } else {
            if (movies.isNotEmpty()) {
                movies.removeAt(movies.size - 1)
            }
            movies.clear()
            movies.addAll(newMovies)
        }

        notifyDataSetChanged()
    }
}
