package io.viewpoint.giphy.api.services

import io.viewpoint.giphy.api.models.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApi {
    @GET("gifs/trending")
    suspend fun getGifTrendings(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): TrendingResponse

    @GET("stickers/trending")
    suspend fun getStickersTrendings(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): TrendingResponse
}