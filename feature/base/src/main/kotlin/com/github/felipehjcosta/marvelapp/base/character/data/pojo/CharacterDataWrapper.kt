package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataWrapper(
    @SerialName("code") var code: Int = 0,
    @SerialName("status") var status: String = "",
    @SerialName("copyright") var copyright: String = "",
    @SerialName("attributionText") var attributionText: String = "",
    @SerialName("attributionHTML") var attributionHTML: String = "",
    @SerialName("data") var characterDataContainer: CharacterDataContainer = CharacterDataContainer(),
    @SerialName("etag") var etag: String = ""
)
