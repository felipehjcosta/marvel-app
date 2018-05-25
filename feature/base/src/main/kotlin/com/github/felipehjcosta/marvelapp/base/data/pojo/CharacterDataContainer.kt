package com.github.felipehjcosta.marvelapp.base.data.pojo

import com.google.gson.annotations.SerializedName


class CharacterDataContainer {

    @SerializedName("offset")
    var offset: Int = 0

    @SerializedName("limit")
    var limit: Int = 0

    @SerializedName("total")
    var total: Int = 0

    @SerializedName("count")
    var count: Int = 0

    @SerializedName("results")
    var characters: List<Character> = emptyList()

}