package io.viewpoint.giphy.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import io.viewpoint.giphy.R
import io.viewpoint.giphy.databinding.ItemTrendingBinding
import io.viewpoint.giphy.domain.model.Media

private val diffCallback = object : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem === newItem

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem == newItem
}

class TrendingAdapter : PagingDataAdapter<Media, TrendingAdapter.TrendingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder =
        TrendingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_trending,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        with(holder.binding) {
            val context = this.root.context

            val shimmer = Shimmer.AlphaHighlightBuilder()
                .setDuration(1300)
                .setBaseAlpha(0.3f)
                .setHighlightAlpha(0.6f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()

            Glide.with(context)
                .load(getItem(position)?.urlWithoutQuery)
                .placeholder(ShimmerDrawable().apply {
                    setShimmer(shimmer)
                })
                .into(image)

            executePendingBindings()
        }
    }

    class TrendingViewHolder(val binding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)
}

/**
 * remove query parameters for Glide's cache
 */
private val Media.urlWithoutQuery: Uri?
    get() = Uri.parse(this.url)
        .buildUpon()
        .clearQuery()
        .build()