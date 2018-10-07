package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Url(
        @Optional @SerialName("type") var type: String = "",
        @Optional @SerialName("url") var url: String = ""
)
