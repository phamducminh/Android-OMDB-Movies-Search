package com.pdminh.omdbmoviessearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.model.Movie

/**
 * View Holder for a [Movie] RecyclerView list item.
 */
class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imagePoster: ImageView = itemView.findViewById(R.id.image_poster)
    private val textTitle: TextView = itemView.findViewById(R.id.text_title)
    private val textYear: TextView = itemView.findViewById(R.id.text_year)

    @SuppressLint("SetTextI18n")
    fun bind(movie: Movie?) {
        movie?.let {
            textTitle.text = it.title
            textYear.text = it.year
            Glide.with(imagePoster.context)
                .load(if (it.poster.isEmpty() || it.poster == "N/A") null else it.poster)
                .centerCrop()
                .thumbnail(0.5f)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_movie_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imagePoster)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_movie, parent, false)
            return MovieViewHolder(view)
        }
    }
}