package io.viewpoint.giphy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.giphy.databinding.ActivityMainBinding
import io.viewpoint.giphy.domain.MediaType
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

    private val trendingAdapter = TrendingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.trendings.adapter = trendingAdapter

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