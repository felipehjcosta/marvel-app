package com.felipecosta.kotlinrxjavasample.listing.model.marvel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


class Character : Serializable, Parcelable {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("modified")
    var modified: Date? = null

    @SerializedName("resourceURI")
    var resourceURI: String? = null

    @SerializedName("urls")
    var urls: List<Url>? = null

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null

    @SerializedName("comics")
    var comics: ComicList? = null

    @SerializedName("stories")
    var stories: StoryList? = null

    @SerializedName("events")
    var events: EventList? = null

    @SerializedName("series")
    var series: SeriesList? = null

    companion object {

        @JvmField val CREATOR: Parcelable.Creator<Character> = object : Parcelable.Creator<Character> {
            override fun createFromParcel(source: Parcel): Character = Character(source)
            override fun newArray(size: Int): Array<Character?> = arrayOfNulls(size)
        }
    }

    constructor()

    constructor(source: Parcel) : this()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {}
}