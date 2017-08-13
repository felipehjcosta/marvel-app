package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName
import java.util.*

class ComicList {
    @SerializedName("available")
    var available: Int? = null
    @SerializedName("returned")
    var returned: Int? = null
    @SerializedName("collectionURI")
    var collectionURI: String = ""
    @SerializedName("items")
    var items: List<Summary> = ArrayList()
}
