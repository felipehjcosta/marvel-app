package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StoryList(
        @Optional @SerialName("available") var available: Int = 0,
        @Optional @SerialName("returned") var returned: Int = 0,
        @Optional @SerialName("collectionURI") var collectionURI: String = "",
        @Optional @SerialName("items") var items: List<Summary> = emptyList()
)
