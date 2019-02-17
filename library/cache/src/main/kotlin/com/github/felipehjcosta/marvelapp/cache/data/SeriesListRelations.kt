package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesListRelations(

    @Embedded
    var seriesListEntity: SeriesListEntity = SeriesListEntity(),

    @Relation(
        parentColumn = "series_list_id",
        entityColumn = "summary_series_list_id",
        entity = SummaryEntity::class
    )
    var seriesListSummary: List<SummaryEntity> = mutableListOf()

)
