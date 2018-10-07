package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Thumbnail(
        @Optional @SerialName("path") var path: String = "",
        @Optional @SerialName("extension") var extension: String = ""
) {
    @Transient
    val url: String
        get() = "${path.replace("http://", "https://")}.$extension"
}