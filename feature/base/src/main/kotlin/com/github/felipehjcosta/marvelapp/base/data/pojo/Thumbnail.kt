package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Thumbnail(
        @Optional var path: String = "",
        @Optional var extension: String = ""
) {
    @Transient
    val url: String
        get() = "${path.replace("http://", "https://")}.$extension"
}