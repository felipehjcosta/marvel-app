package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class StorySummary : Serializable, Parcelable {
    @SerializedName("resourceURI")
    var resourceURI: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("type")
    var type: String = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.resourceURI)
        dest.writeString(this.name)
        dest.writeString(this.type)
    }

    constructor() {}

    constructor(`in`: Parcel) {
        this.resourceURI = `in`.readString()
        this.name = `in`.readString()
        this.type = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StorySummary> = object : Parcelable.Creator<StorySummary> {
            override fun createFromParcel(source: Parcel): StorySummary = StorySummary(source)
            override fun newArray(size: Int): Array<StorySummary?> = arrayOfNulls(size)
        }
    }
}