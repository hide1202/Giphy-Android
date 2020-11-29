package io.viewpoint.giphy.domain.repository

import io.viewpoint.giphy.api.services.TrendingApi
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.domain.model.PagingParameter
import io.viewpoint.giphy.domain.model.PagingResult
import javax.inject.Inject

class GiphyMediaRepository @Inject constructor(
    private val trendingApi: TrendingApi
) : MediaRepository {
    override suspend fun getTrendings(
        type: MediaType,
        paging: PagingParameter
    ): PagingResult<List<Media>> {
        val response = when (type) {
            MediaType.GIF -> trendingApi.getGifTrendings(paging.offset, paging.count)
            MediaType.STICKER -> trendingApi.getStickersTrendings(paging.offset, paging.count)
        }
        val pagination = response.pagination
        return response
            .data
            .map {
                Media(
                    type = type,
                    url = it.images?.fixed_height?.url
                )
            }.let {
                val totalPage = pagination.total_count / pagination.count
                PagingResult(
                    data = it,
                    currentOffset = pagination.offset,
                    nextOffset = (pagination.offset + 1).takeIf { next ->
                        next < totalPage
                    }
                )
            }
    }
}