package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName

class Thumbnail {
    @SerializedName("path")
    var path: String = ""
    @SerializedName("extension")
    var extension: String = ""

    val url: String
        get() = "$path.$extension"
}