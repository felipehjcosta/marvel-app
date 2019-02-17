package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.Embedded
import androidx.room.Relation

data class ComicListRelations(

    @Embedded
    var comicList: ComicListEntity = ComicListEntity(),

    @Relation(
        parentColumn = "comic_list_id",
        entityColumn = "summary_comic_list_id",
        entity = SummaryEntity::class
    )
    var comicListSummary: List<SummaryEntity> = mutableListOf()

)
