package com.github.felipehjcosta.marvelapp.base.data.pojo

import com.google.gson.annotations.SerializedName

class Thumbnail {
    @SerializedName("path")
    var path: String = ""
    @SerializedName("extension")
    var extension: String = ""

    val url: String
        get() = "${path.replace("http://", "https://")}.$extension"
}