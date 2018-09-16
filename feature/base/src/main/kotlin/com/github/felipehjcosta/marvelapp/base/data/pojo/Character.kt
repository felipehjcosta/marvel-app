package com.github.felipehjcosta.marvelapp.base.data.pojo

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class Character(
        @Optional var id: Int = -1,
        @Optional var name: String = "",
        @Optional var description: String = "",
        @Optional var resourceURI: String = "",
        @Optional var urls: List<Url> = emptyList(),
        @Optional var thumbnail: Thumbnail = Thumbnail(),
        @Optional var comics: ComicList = ComicList(),
        @Optional var stories: StoryList = StoryList(),
        @Optional var events: EventList = EventList(),
        @Optional var series: SeriesList = SeriesList()
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