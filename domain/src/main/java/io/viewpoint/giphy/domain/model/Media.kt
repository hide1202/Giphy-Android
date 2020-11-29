package io.viewpoint.giphy.domain.model

import io.viewpoint.giphy.domain.MediaType

data class Media(
    val type: MediaType,
    val url: String?
)