package io.viewpoint.giphy.home

import androidx.paging.PagingSource
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.domain.model.PagingParameter
import io.viewpoint.giphy.domain.repository.MediaRepository

class TrendingPagingSource(
    private val type: MediaType,
    private val repository: MediaRepository
) : PagingSource<Int, Media>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Media> {
        return try {
            val result = repository.getTrendings(
                type, PagingParameter(
                    offset = params.key ?: 0,
                    count = params.loadSize
                )
            )
            LoadResult.Page(
                result.data,
                prevKey = (result.currentOffset - 1).takeIf {
                    it >= 0
                },
                nextKey = result.nextOffset
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}