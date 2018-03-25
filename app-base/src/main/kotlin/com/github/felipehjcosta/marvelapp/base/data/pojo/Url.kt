package com.github.felipehjcosta.marvelapp.base.data.pojo

import com.google.gson.annotations.SerializedName

class Url {
    @SerializedName("type")
    var type: String = ""
    @SerializedName("url")
    var url: String = ""
}
