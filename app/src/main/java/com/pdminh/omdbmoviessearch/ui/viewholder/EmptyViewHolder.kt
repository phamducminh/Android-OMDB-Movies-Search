package com.pdminh.omdbmoviessearch.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.util.show

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val message: TextView = itemView.findViewById(R.id.message)

    fun showEmptyMessage(errMessage: String) {
        image.show()
        message.also {
            it.text = errMessage
            it.show()
        }
    }

    companion object {
        fun create(parent: ViewGroup): EmptyViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item_empty, parent, false)
            return EmptyViewHolder(view)
        }
    }
}