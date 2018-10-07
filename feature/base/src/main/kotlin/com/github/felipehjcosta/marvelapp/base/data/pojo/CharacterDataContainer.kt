package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataContainer(
        @Optional @SerialName("offset") var offset: Int = 0,
        @Optional @SerialName("limit") var limit: Int = 0,
        @Optional @SerialName("total") var total: Int = 0,
        @Optional @SerialName("count") var count: Int = 0,
        @Optional @SerialName("results") var characters: List<Character> = emptyList()
)