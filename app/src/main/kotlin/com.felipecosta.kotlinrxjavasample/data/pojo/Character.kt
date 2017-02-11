package com.felipecosta.kotlinrxjavasample.data.pojo

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.*


class Character : Serializable, Parcelable {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("modified")
    var modified: Date? = null

    @SerializedName("resourceURI")
    var resourceURI: String = ""

    @SerializedName("urls")
    var urls: List<Url> = ArrayList()

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

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(this.id)
        dest.writeString(this.name)
        dest.writeString(this.description)
        dest.writeLong(if (this.modified != null) (this.modified as Date).time else -1)
        dest.writeString(this.resourceURI)
        dest.writeTypedList(this.urls)
        dest.writeParcelable(this.thumbnail, flags)
        dest.writeParcelable(this.comics, flags)
        dest.writeParcelable(this.stories, flags)
        dest.writeParcelable(this.events, flags)
        dest.writeParcelable(this.series, flags)
    }

    constructor() {
    }

    constructor(`in`: Parcel) {
        this.id = `in`.readValue(Int::class.java.classLoader) as Int
        this.name = `in`.readString()
        this.description = `in`.readString()
        val tmpModified = `in`.readLong()
        this.modified = if (tmpModified == -1L) null else Date(tmpModified)
        this.resourceURI = `in`.readString()
        this.urls = `in`.createTypedArrayList(Url.CREATOR)
        this.thumbnail = `in`.readParcelable<Thumbnail>(Thumbnail::class.java.classLoader)
        this.comics = `in`.readParcelable<ComicList>(ComicList::class.java.classLoader)
        this.stories = `in`.readParcelable<StoryList>(StoryList::class.java.classLoader)
        this.events = `in`.readParcelable<EventList>(EventList::class.java.classLoader)
        this.series = `in`.readParcelable<SeriesList>(SeriesList::class.java.classLoader)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Character> = object : Parcelable.Creator<Character> {
            override fun createFromParcel(source: Parcel): Character = Character(source)
            override fun newArray(size: Int): Array<Character?> = arrayOfNulls(size)
        }
    }
}