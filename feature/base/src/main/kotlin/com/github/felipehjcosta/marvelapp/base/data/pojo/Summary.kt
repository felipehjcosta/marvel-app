package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Summary(
        @Optional @SerialName("resourceURI") var resourceURI: String = "",
        @Optional @SerialName("name") var name: String = "",
        @Optional @SerialName("type") var type: String = ""
)