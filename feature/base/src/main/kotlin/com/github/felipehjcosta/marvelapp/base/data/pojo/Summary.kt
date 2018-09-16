package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class Summary(
        @Optional var resourceURI: String = "",
        @Optional var name: String = "",
        @Optional var type: String = ""
)