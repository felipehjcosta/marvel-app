package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Url(
    @SerialName("type") var type: String = "",
    @SerialName("url") var url: String = ""
)
