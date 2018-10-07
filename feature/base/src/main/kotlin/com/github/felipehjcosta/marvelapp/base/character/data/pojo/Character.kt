package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Character(
        @Optional @SerialName("id") var id: Int = -1,
        @Optional @SerialName("name") var name: String = "",
        @Optional @SerialName("description") var description: String = "",
        @Optional @SerialName("resourceURI") var resourceURI: String = "",
        @Optional @SerialName("urls") var urls: List<Url> = emptyList(),
        @Optional @SerialName("thumbnail") var thumbnail: Thumbnail = Thumbnail(),
        @Optional @SerialName("comics") var comics: ComicList = ComicList(),
        @Optional @SerialName("stories") var stories: StoryList = StoryList(),
        @Optional @SerialName("events") var events: EventList = EventList(),
        @Optional @SerialName("series") var series: SeriesList = SeriesList()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id
}