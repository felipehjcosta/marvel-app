package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Thumbnail : Serializable, Parcelable {
    @SerializedName("path")
    var path: String = ""
    @SerializedName("extension")
    var extension: String = ""

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.path)
        dest.writeString(this.extension)
    }

    constructor() {}

    constructor(`in`: Parcel) {
        this.path = `in`.readString()
        this.extension = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Thumbnail> = object : Parcelable.Creator<Thumbnail> {
            override fun createFromParcel(source: Parcel): Thumbnail = Thumbnail(source)
            override fun newArray(size: Int): Array<Thumbnail?> = arrayOfNulls(size)
        }
    }
}