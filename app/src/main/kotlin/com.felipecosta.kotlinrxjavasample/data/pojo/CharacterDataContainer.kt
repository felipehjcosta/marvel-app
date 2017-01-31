package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CharacterDataContainer : Serializable {

    @SerializedName("offset")
    var offset: Int? = null

    @SerializedName("limit")
    var limit: Int? = null

    @SerializedName("total")
    var total: Int? = null

    @SerializedName("count")
    var count: Int? = null

    @SerializedName("results")
    var characters: List<Character>? = null

}