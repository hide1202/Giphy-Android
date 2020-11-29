package io.viewpoint.giphy.api.models

data class TrendingResponse(
    val `data`: List<Gif>,
    val meta: Meta,
    val pagination: Pagination
)