package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataContainer(
        @Optional var offset: Int = 0,
        @Optional var limit: Int = 0,
        @Optional var total: Int = 0,
        @Optional var count: Int = 0,
        @Optional @SerialName("results") var characters: List<Character> = emptyList()
)