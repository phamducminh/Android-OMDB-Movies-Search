package com.pdminh.omdbmoviessearch.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.util.show

/**
 * View Holder for loading list movie in RecyclerView.
 */
class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

    fun bind() {
        progressBar.show()
    }

    companion object {
        fun create(parent: ViewGroup): LoadingViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.loading_view_item, parent, false)
            return LoadingViewHolder(view)
        }
    }
}