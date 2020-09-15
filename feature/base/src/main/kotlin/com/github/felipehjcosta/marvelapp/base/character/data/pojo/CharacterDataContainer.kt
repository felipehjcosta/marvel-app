package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CharacterDataContainer(
    @SerialName("offset") var offset: Int = 0,
    @SerialName("limit") var limit: Int = 0,
    @SerialName("total") var total: Int = 0,
    @SerialName("count") var count: Int = 0,
    @SerialName("results") var characters: List<Character> = emptyList()
)
