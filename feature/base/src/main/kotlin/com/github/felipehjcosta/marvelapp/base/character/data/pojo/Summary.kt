package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Summary(
    @SerialName("resourceURI") var resourceURI: String = "",
    @SerialName("name") var name: String = "",
    @SerialName("type") var type: String = ""
)
