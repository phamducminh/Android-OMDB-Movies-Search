package com.pdminh.omdbmoviessearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.model.MovieItem
import com.pdminh.omdbmoviessearch.util.show

/**
 * Adapter for the list of repositories.
 */
class MovieSearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies = ArrayList<MovieItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
            MovieViewHolder(view)
        } else {
            val view: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_lazy_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> movies[position]?.let { holder.bindItems(it) }
            is LoadingViewHolder -> holder.showLoadingView()
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (movies[position] != null) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING

    fun setData(newMovies: ArrayList<MovieItem?>?) {
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

    fun getData() = movies

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagePoster: ImageView = itemView.findViewById(R.id.image_poster)
        private val textTitle: TextView = itemView.findViewById(R.id.text_title)
        private val textYear: TextView = itemView.findViewById(R.id.text_year)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: MovieItem) {
            textTitle.text = movie.title
            textYear.text = movie.year
            Glide.with(imagePoster.context).load(movie.poster)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagePoster)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.show()
        }
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }
}
