package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName


class CharacterDataWrapper {

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("status")
    var status: String = ""

    @SerializedName("copyright")
    var copyright: String = ""

    @SerializedName("attributionText")
    var attributionText: String = ""

    @SerializedName("attributionHTML")
    var attributionHTML: String = ""

    @SerializedName("data")
    var characterDataContainer: CharacterDataContainer = CharacterDataContainer()

    @SerializedName("etag")
    var etag: String = ""

}