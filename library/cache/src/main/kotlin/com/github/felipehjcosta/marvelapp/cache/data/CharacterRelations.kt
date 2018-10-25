package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class CharacterRelations(

        @Embedded
        var character: CharacterEntity = CharacterEntity(),

        @Relation(
                parentColumn = "id",
                entityColumn = "url_character_id",
                entity = UrlEntity::class)
        var urls: List<UrlEntity> = mutableListOf(),


        @Embedded
        var thumbnail: ThumbnailEntity = ThumbnailEntity(),

        @Embedded
        var comicListRelations: ComicListRelations = ComicListRelations(),

        @Embedded
        var storyListRelations: StoryListRelations = StoryListRelations(),

        @Embedded
        var eventListRelations: EventListRelations = EventListRelations(),

        @Embedded
        var seriesListRelations: SeriesListRelations = SeriesListRelations()

)