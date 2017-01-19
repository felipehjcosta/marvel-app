package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class EventList : Serializable, Parcelable {
    @SerializedName("available")
    var available: Int? = null

    @SerializedName("returned")
    var returned: Int? = null

    @SerializedName("collectionURI")
    var collectionURI: String? = null

    @SerializedName("items")
    var items: List<EventSummary>? = null

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<EventList> = object : Parcelable.Creator<EventList> {
            override fun createFromParcel(source: Parcel): EventList = EventList(source)
            override fun newArray(size: Int): Array<EventList?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}