package com.pdminh.omdbmoviessearch.util

import android.app.Activity
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Can show [Toast] from every [Activity].
 */
fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

/**
 * Returns Color from resource.
 * @param id Color Resource ID
 */
fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)