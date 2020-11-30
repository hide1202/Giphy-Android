package io.viewpoint.giphy.extensions

import android.view.View
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter

@BindingAdapter("gone")
fun View.setGone(gone: Boolean) {
    this.isGone = gone
}