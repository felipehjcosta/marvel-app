package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataWrapper(
        @Optional @SerialName("code") var code: Int = 0,
        @Optional @SerialName("status") var status: String = "",
        @Optional @SerialName("copyright") var copyright: String = "",
        @Optional @SerialName("attributionText") var attributionText: String = "",
        @Optional @SerialName("attributionHTML") var attributionHTML: String = "",
        @Optional @SerialName("data") var characterDataContainer: CharacterDataContainer = CharacterDataContainer(),
        @Optional @SerialName("etag") var etag: String = ""
)