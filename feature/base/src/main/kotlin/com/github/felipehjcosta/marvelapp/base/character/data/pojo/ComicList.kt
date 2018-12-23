package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ComicList(
    @Optional @SerialName("available") var available: Int = 0,
    @Optional @SerialName("returned") var returned: Int = 0,
    @Optional @SerialName("collectionURI") var collectionURI: String = "",
    @Optional @SerialName("items") var items: List<Summary> = emptyList()
)
