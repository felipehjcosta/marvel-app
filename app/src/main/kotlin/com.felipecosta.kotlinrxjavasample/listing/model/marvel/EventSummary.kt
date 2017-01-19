package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EventSummary : Serializable, Parcelable {
    @SerializedName("resourceURI")
    var resourceURI: String? = null

    @SerializedName("name")
    var name: String? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<EventSummary> = object : Parcelable.Creator<EventSummary> {
            override fun createFromParcel(source: Parcel): EventSummary = EventSummary(source)
            override fun newArray(size: Int): Array<EventSummary?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}