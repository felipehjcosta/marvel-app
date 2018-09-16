package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class Url(
        @Optional var type: String = "",
        @Optional var url: String = ""
)
