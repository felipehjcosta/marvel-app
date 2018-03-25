package com.github.felipehjcosta.marvelapp.base.data.pojo

import com.google.gson.annotations.SerializedName

class Summary {
    @SerializedName("resourceURI")
    var resourceURI: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("type")
    var type: String = ""
}