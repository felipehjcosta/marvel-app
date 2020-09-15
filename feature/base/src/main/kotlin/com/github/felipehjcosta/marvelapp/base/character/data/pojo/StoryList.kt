package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoryList(
    @SerialName("available") var available: Int = 0,
    @SerialName("returned") var returned: Int = 0,
    @SerialName("collectionURI") var collectionURI: String = "",
    @SerialName("items") var items: List<Summary> = emptyList()
)
