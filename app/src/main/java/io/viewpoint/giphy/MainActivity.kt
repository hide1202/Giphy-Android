package io.viewpoint.giphy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.giphy.databinding.ActivityMainBinding
import io.viewpoint.giphy.databinding.ItemTrendingBinding
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.home.TrendingAdapter
import io.viewpoint.giphy.home.TrendingViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val trendingViewModel: TrendingViewModel by viewModels()

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

        lifecycleScope.launch {
            trendingAdapter.loadStateFlow
                .map {
                    it.refresh
                }
                .collect {
                    binding.refreshing = it is LoadState.Loading
                }
        }

        binding.refreshLayout.setOnRefreshListener {
            trendingAdapter.refresh()
        }

        binding.toTop.setOnClickListener {
            binding.trendings.smoothScrollToPosition(0)
        }

        loadApi()
    }

    private fun loadApi() {
        lifecycleScope.launch {
            trendingViewModel.flowWithMediaType(MediaType.GIF)
                .collect {
                    trendingAdapter.submitData(it)
                }
        }
    }
}