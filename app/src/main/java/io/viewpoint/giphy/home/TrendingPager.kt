package io.viewpoint.giphy.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.repository.MediaRepository
import javax.inject.Inject

class TrendingPager @Inject constructor(
    private val repository: MediaRepository
) {
    fun withMediaType(type: MediaType) = Pager(
        config = PagingConfig(pageSize = 25),
        initialKey = 0,
        pagingSourceFactory = {
            TrendingPagingSource(type, repository)
        }
    )
}