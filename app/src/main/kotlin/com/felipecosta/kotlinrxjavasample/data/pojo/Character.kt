package com.felipecosta.kotlinrxjavasample.data.pojo

import com.google.gson.annotations.SerializedName


class Character {
    @SerializedName("id")
    var id: Int = -1

    @SerializedName("name")
    var name: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("resourceURI")
    var resourceURI: String = ""

    @SerializedName("urls")
    var urls: List<Url> = emptyList()

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail = Thumbnail()

    @SerializedName("comics")
    var comics: ComicList = ComicList()

    @SerializedName("stories")
    var stories: StoryList = StoryList()

    @SerializedName("events")
    var events: EventList = EventList()

    @SerializedName("series")
    var series: SeriesList = SeriesList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id
}