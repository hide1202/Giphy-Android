package io.viewpoint.giphy.domain.repository

import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media

interface MediaRepository {
    suspend fun getTrendings(type: MediaType): List<Media>
}