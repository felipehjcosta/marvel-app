package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class SeriesSummary : Serializable, Parcelable {
    @SerializedName("resourceURI")
    var resourceURI: String = ""
    @SerializedName("name")
    var name: String = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.resourceURI)
        dest.writeString(this.name)
    }

    constructor() {}

    constructor(`in`: Parcel) {
        this.resourceURI = `in`.readString()
        this.name = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SeriesSummary> = object : Parcelable.Creator<SeriesSummary> {
            override fun createFromParcel(source: Parcel): SeriesSummary = SeriesSummary(source)
            override fun newArray(size: Int): Array<SeriesSummary?> = arrayOfNulls(size)
        }
    }
}
