package io.viewpoint.giphy.api.models

data class Pagination(
    val count: Int,
    val offset: Int,
    val total_count: Int
)