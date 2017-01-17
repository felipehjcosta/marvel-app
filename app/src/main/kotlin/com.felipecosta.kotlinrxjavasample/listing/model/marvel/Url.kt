package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Url : Serializable {

    @SerializedName("type")
    var type: String? = null

    @SerializedName("url")
    var url: String? = null


}