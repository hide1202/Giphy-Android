package io.viewpoint.giphy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.giphy.databinding.ActivityMainBinding
import io.viewpoint.giphy.databinding.ItemTrendingBinding
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.domain.repository.MediaRepository
import io.viewpoint.giphy.home.TrendingAdapter
import io.viewpoint.giphy.home.TrendingPager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mediaRepository: MediaRepository

    private val trendingAdapter = TrendingAdapter(object : TrendingAdapter.Callback {
        override fun onGifClicked(binding: ItemTrendingBinding, media: Media) {
            val activity = this@MainActivity
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                binding.image,
                binding.image.transitionName
            )

            val intent = GifDetailActivity.intent(activity, media)
            startActivity(intent, options.toBundle())
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.trendings.adapter = trendingAdapter
        binding.trendings.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.toTopVisible = recyclerView.canScrollVertically(-1)
            }
        })

        binding.toTop.setOnClickListener {
            binding.trendings.smoothScrollToPosition(0)
        }

        loadApi()
    }

    private fun loadApi() {
        val pager = TrendingPager(mediaRepository)
        lifecycleScope.launch {
            pager.withMediaType(MediaType.GIF)
                .flow
                .collect {
                    trendingAdapter.submitData(it)
                }
        }
    }
}