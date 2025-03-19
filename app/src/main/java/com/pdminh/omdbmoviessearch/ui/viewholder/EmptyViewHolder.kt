package com.pdminh.omdbmoviessearch.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.R

class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val message: TextView = itemView.findViewById(R.id.message)

    fun bind(errMessage: String) {
        message.text = errMessage
    }

    companion object {
        fun create(parent: ViewGroup): EmptyViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.empty_view_item, parent, false)
            return EmptyViewHolder(view)
        }
    }
}