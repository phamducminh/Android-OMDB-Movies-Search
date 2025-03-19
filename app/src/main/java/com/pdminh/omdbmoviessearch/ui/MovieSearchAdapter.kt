package com.pdminh.omdbmoviessearch.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.model.Movie
import com.pdminh.omdbmoviessearch.ui.viewholder.EmptyViewHolder
import com.pdminh.omdbmoviessearch.ui.viewholder.LoadingViewHolder
import com.pdminh.omdbmoviessearch.ui.viewholder.MovieViewHolder
import javax.inject.Inject

/**
 * Adapter for the list of repositories.
 */
class MovieSearchAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = mutableListOf<Movie?>()
    private var errMessage = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> MovieViewHolder.create(parent)
            VIEW_TYPE_LOADING -> LoadingViewHolder.create(parent)
            VIEW_TYPE_EMPTY -> EmptyViewHolder.create(parent)
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> movies[position]?.let { holder.bind(it) }
            is LoadingViewHolder -> holder.showLoadingView()
            is EmptyViewHolder -> holder.showEmptyMessage(errMessage)
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        errMessage.isNotEmpty() -> VIEW_TYPE_EMPTY
        movies[position] == null -> VIEW_TYPE_LOADING
        else -> VIEW_TYPE_ITEM
    }

    fun setData(newMovies: List<Movie?>?, errorMessage: String = "") {
        if (newMovies != null) {
            if (movies.isNotEmpty()) {
                movies.removeAt(movies.size - 1)
            }
            movies.clear()
            movies.addAll(newMovies)
        } else {
            if (errorMessage.isNotEmpty()) {
                movies.clear()
            }
            movies.add(null)
        }

        errMessage = errorMessage

        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        private const val VIEW_TYPE_EMPTY = 2
    }
}
