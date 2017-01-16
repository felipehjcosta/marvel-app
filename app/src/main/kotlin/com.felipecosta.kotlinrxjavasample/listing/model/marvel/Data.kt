package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Data : Serializable {

    @SerializedName("offset")
    var offset: Int? = null

    @SerializedName("limit")
    var limit: Int? = null

    @SerializedName("total")
    var total: Int? = null

    @SerializedName("count")
    var count: Int? = null

    @SerializedName("results")
    var results: List<Results>? = null


}