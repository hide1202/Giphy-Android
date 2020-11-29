package io.viewpoint.giphy.domain.model

data class PagingResult<T>(
    val data: T,
    val currentOffset: Int,
    val nextOffset: Int?
)