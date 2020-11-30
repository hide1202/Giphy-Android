package io.viewpoint.giphy.extensions

import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.BaseRequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

fun <TranscodeType> BaseRequestOptions<RequestBuilder<TranscodeType>>.placeHolderWithShimmer(): RequestBuilder<TranscodeType> {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(1300)
        .setBaseAlpha(0.3f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    return placeholder(
        ShimmerDrawable()
            .apply {
                setShimmer(shimmer)
            }
    )
}