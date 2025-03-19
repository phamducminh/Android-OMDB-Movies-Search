package com.pdminh.omdbmoviessearch.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.model.Movie
import javax.inject.Inject

/**
 * Adapter for the list of repositories.
 */
class MovieSearchAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = ArrayList<Movie?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            MovieViewHolder.create(parent)
        } else {
            LoadingViewHolder.create(parent)
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> movies[position]?.let { holder.bind(it) }
            is LoadingViewHolder -> holder.showLoadingView()
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (movies[position] != null) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING

    fun setData(newMovies: ArrayList<Movie?>?) {
        if (newMovies != null) {
            if (movies.isNotEmpty()) {
                movies.removeAt(movies.size - 1)
            }
            movies.clear()
            movies.addAll(newMovies)
        } else {
            movies.add(newMovies)
        }
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}
