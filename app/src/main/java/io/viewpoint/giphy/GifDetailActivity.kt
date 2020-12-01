package io.viewpoint.giphy

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.giphy.databinding.ActivityGifDetailBinding
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.extensions.placeHolderWithShimmer

@AndroidEntryPoint
class GifDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGifDetailBinding

    private val media: Media by lazy {
        intent?.getSerializableExtra(EXTRA_MEDIA) as? Media ?: throw IllegalArgumentException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gif_detail)
        binding.media = media

        supportPostponeEnterTransition()

        Glide.with(this)
            .load(media.url)
            .dontTransform()
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .placeHolderWithShimmer()
            .into(binding.image)
    }

    companion object {
        private const val EXTRA_MEDIA = "media"

        fun intent(context: Context, media: Media): Intent =
            Intent(context, GifDetailActivity::class.java).apply {
                putExtra(EXTRA_MEDIA, media)
            }
    }
}