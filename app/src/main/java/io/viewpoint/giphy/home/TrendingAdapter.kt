package io.viewpoint.giphy.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.viewpoint.giphy.R
import io.viewpoint.giphy.databinding.ItemTrendingBinding
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.extensions.placeHolderWithShimmer

private val diffCallback = object : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem === newItem

    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem == newItem
}

class TrendingAdapter(
    private val callback: Callback
) : PagingDataAdapter<Media, TrendingAdapter.TrendingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder =
        TrendingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_trending,
                parent,
                false
            )
        ).also { vh ->
            vh.binding.image.setOnClickListener {
                val position = vh.bindingAdapterPosition.takeIf {
                    it != RecyclerView.NO_POSITION
                } ?: return@setOnClickListener
                val item = getItem(position) ?: return@setOnClickListener
                callback.onGifClicked(item)
            }
        }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        with(holder.binding) {
            val context = this.root.context

            Glide.with(context)
                .load(getItem(position)?.previewUrlWithoutQuery)
                .placeHolderWithShimmer()
                .into(image)

            executePendingBindings()
        }
    }

    class TrendingViewHolder(val binding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface Callback {
        fun onGifClicked(media: Media)
    }
}

/**
 * remove query parameters for Glide's cache
 */
private val Media.urlWithoutQuery: Uri?
    get() = Uri.parse(this.url)
        .buildUpon()
        .clearQuery()
        .build()

/**
 * remove query parameters for Glide's cache
 */
private val Media.previewUrlWithoutQuery: Uri?
    get() = Uri.parse(this.previewUrl)
        .buildUpon()
        .clearQuery()
        .build()