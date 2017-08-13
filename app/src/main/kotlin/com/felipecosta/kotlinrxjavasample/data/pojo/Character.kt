package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName
import java.util.*


class Character {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}