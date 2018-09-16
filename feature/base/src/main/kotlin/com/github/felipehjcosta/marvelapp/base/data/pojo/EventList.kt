package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class EventList(
        @Optional var available: Int = 0,
        @Optional var returned: Int = 0,
        @Optional var collectionURI: String = "",
        @Optional var items: List<Summary> = emptyList()
)
