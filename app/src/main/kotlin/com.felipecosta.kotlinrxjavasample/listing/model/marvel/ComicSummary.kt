package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ComicSummary : Serializable, Parcelable {
    @SerializedName("resourceURI")
    var resourceURI: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("type")
    var type: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ComicSummary> = object : Parcelable.Creator<ComicSummary> {
            override fun createFromParcel(source: Parcel): ComicSummary = ComicSummary(source)
            override fun newArray(size: Int): Array<ComicSummary?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}