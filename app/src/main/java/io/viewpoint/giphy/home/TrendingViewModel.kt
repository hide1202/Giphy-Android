package io.viewpoint.giphy.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

class TrendingViewModel @ViewModelInject constructor(
    mediaRepository: MediaRepository
) : ViewModel() {
    private val pager = TrendingPager(mediaRepository)

    fun flowWithMediaType(
        type: MediaType
    ): Flow<PagingData<Media>> = pager
        .withMediaType(type)
        .flow
}