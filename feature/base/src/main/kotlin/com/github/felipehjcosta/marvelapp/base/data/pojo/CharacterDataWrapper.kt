package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataWrapper(
        @Optional var code: Int = 0,
        @Optional var status: String = "",
        @Optional var copyright: String = "",
        @Optional var attributionText: String = "",
        @Optional var attributionHTML: String = "",
        @Optional @SerialName("data") var characterDataContainer: CharacterDataContainer = CharacterDataContainer(),
        @Optional var etag: String = ""
)