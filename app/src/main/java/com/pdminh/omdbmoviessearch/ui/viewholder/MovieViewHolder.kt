package com.pdminh.omdbmoviessearch.ui.viewholder

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private val TAG = "MovieViewHolder"
    private val imagePoster: ImageView = itemView.findViewById(R.id.image_poster)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val year: TextView = itemView.findViewById(R.id.year)
    private val button: Button = itemView.findViewById(R.id.button)
    private val label: TextView = itemView.findViewById(R.id.label)

    @SuppressLint("SetTextI18n")
    fun bind(movie: Movie) {
        title.text = movie.title
        year.text = movie.year
        Glide.with(imagePoster.context)
            .load(if (movie.poster.isEmpty() || movie.poster == "N/A") null else movie.poster)
            .centerCrop()
            .thumbnail(0.5f)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_movie_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(imagePoster)

        button.setOnClickListener {
            Log.d(TAG, "Click me button clicked")
            // Show/hide label
            // label.visibility = if (label.isGone) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_view_item, parent, false)
            return MovieViewHolder(view)
        }
    }
}