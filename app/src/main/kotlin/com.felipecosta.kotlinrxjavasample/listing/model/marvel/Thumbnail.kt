package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Thumbnail : Serializable, Parcelable {
    @SerializedName("path")
    var path: String? = null

    @SerializedName("extension")
    var extension: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Thumbnail> = object : Parcelable.Creator<Thumbnail> {
            override fun createFromParcel(source: Parcel): Thumbnail = Thumbnail(source)
            override fun newArray(size: Int): Array<Thumbnail?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}