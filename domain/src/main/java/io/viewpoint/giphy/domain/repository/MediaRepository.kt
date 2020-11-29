package io.viewpoint.giphy.domain.repository

import io.viewpoint.giphy.domain.MediaType
import io.viewpoint.giphy.domain.model.Media
import io.viewpoint.giphy.domain.model.PagingParameter
import io.viewpoint.giphy.domain.model.PagingResult

interface MediaRepository {
    suspend fun getTrendings(type: MediaType, paging: PagingParameter): PagingResult<List<Media>>
}