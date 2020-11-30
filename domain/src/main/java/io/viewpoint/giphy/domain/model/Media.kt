package io.viewpoint.giphy.domain.model

import io.viewpoint.giphy.domain.MediaType
import java.io.Serializable

data class Media(
    val type: MediaType,
    val title: String,
    val previewUrl: String?,
    val url: String?,
) : Serializable