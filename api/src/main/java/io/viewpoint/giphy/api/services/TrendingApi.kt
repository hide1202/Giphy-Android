package io.viewpoint.giphy.api.services

import io.viewpoint.giphy.api.models.TrendingResponse
import retrofit2.http.GET

interface TrendingApi {
    @GET("gifs/trending")
    suspend fun getGifTrendings(): TrendingResponse

    @GET("stickers/trending")
    suspend fun getStickersTrendings(): TrendingResponse
}