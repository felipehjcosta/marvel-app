package com.github.felipehjcosta.marvelapp.base.data.pojo

import com.google.gson.annotations.SerializedName

class SeriesList {
    @SerializedName("available")
    var available: Int = 0
    @SerializedName("returned")
    var returned: Int = 0
    @SerializedName("collectionURI")
    var collectionURI: String = ""
    @SerializedName("items")
    var items: List<Summary> = emptyList()
}
