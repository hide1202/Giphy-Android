package io.viewpoint.giphy.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.viewpoint.giphy.domain.repository.GiphyMediaRepository
import io.viewpoint.giphy.domain.repository.MediaRepository

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun mediaRepository(repository: GiphyMediaRepository): MediaRepository
}