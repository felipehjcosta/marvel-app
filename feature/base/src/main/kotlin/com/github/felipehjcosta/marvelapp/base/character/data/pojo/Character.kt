package com.github.felipehjcosta.marvelapp.base.character.data.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Character(
    @SerialName("id") var id: Long = -1,
    @SerialName("name") var name: String = "",
    @SerialName("description") var description: String = "",
    @SerialName("resourceURI") var resourceURI: String = "",
    @SerialName("urls") var urls: List<Url> = emptyList(),
    @SerialName("thumbnail") var thumbnail: Thumbnail = Thumbnail(),
    @SerialName("comics") var comics: ComicList = ComicList(),
    @SerialName("stories") var stories: StoryList = StoryList(),
    @SerialName("events") var events: EventList = EventList(),
    @SerialName("series") var series: SeriesList = SeriesList()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Character

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.toInt()
}
