package io.viewpoint.giphy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.giphy.BuildConfig
import io.viewpoint.giphy.api.GiphyApi
import io.viewpoint.giphy.api.services.TrendingApi
import timber.log.Timber

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {
    private val giphyApi: GiphyApi = GiphyApi.Builder()
        .apply {
            debugLog = {
                Timber.d(it)
            }
            apiKey = BuildConfig.API_KEY
        }
        .build()

    @Provides
    fun trendingApi(): TrendingApi = giphyApi.getService()
}