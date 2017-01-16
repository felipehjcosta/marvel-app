package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import com.google.gson.annotations.SerializedName

import java.io.Serializable


class Comics : Serializable {

    @SerializedName("available")
    var available: Int? = null

    @SerializedName("returned")
    var returned: Int? = null

    @SerializedName("collectionURI")
    var collectionURI: String? = null

    @SerializedName("items")
    var items: List<Items>? = null

}