package io.viewpoint.giphy.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import io.viewpoint.giphy.R
import io.viewpoint.giphy.databinding.ItemTrendingBinding
import io.viewpoint.giphy.domain.model.Media

class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {
    private val items = mutableListOf<Media>()

    fun updateItems(newItems: List<Media>) {
        items.clear()
        items.addAll(newItems)

        notifyDataSetChanged()
    }

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
                .load(items[position].url)
                .placeholder(ShimmerDrawable().apply {
                    setShimmer(shimmer)
                })
                .into(image)

            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = items.size

    class TrendingViewHolder(val binding: ItemTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)
}