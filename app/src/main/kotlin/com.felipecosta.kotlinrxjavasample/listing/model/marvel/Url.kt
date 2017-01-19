package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Url : Serializable, Parcelable {
    @SerializedName("type")
    var type: String? = null

    @SerializedName("url")
    var url: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Url> = object : Parcelable.Creator<Url> {
            override fun createFromParcel(source: Parcel): Url = Url(source)
            override fun newArray(size: Int): Array<Url?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}