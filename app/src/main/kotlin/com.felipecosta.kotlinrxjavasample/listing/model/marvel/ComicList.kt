package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.*

class ComicList : Serializable, Parcelable {
    @SerializedName("available")
    var available: Int? = null
    @SerializedName("returned")
    var returned: Int? = null
    @SerializedName("collectionURI")
    var collectionURI: String = ""
    @SerializedName("items")
    var items: List<ComicSummary> = ArrayList()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(this.available)
        dest.writeValue(this.returned)
        dest.writeString(this.collectionURI)
        dest.writeTypedList(this.items)
    }

    constructor() {}

    constructor(`in`: Parcel) {
        this.available = `in`.readValue(Int::class.java.classLoader) as Int
        this.returned = `in`.readValue(Int::class.java.classLoader) as Int
        this.collectionURI = `in`.readString()
        this.items = `in`.createTypedArrayList(ComicSummary.CREATOR)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ComicList> = object : Parcelable.Creator<ComicList> {
            override fun createFromParcel(source: Parcel): ComicList = ComicList(source)
            override fun newArray(size: Int): Array<ComicList?> = arrayOfNulls(size)
        }
    }
}
