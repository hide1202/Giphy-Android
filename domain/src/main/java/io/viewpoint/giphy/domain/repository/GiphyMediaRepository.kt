package io.viewpoint.giphy.domain.repository

import io.viewpoint.giphy.api.services.TrendingApi
import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import javax.inject.Inject

class GiphyMediaRepository @Inject constructor(
    private val trendingApi: TrendingApi
) : MediaRepository {
    override suspend fun getTrendings(type: MediaType): List<Media> =
        when (type) {
            MediaType.GIF -> trendingApi.getGifTrendings()
            MediaType.STICKER -> trendingApi.getStickersTrendings()
        }.data.map {
            Media(
                type = type,
                url = it.images?.fixed_height?.url
            )
        }
}