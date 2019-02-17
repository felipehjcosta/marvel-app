package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.Embedded
import androidx.room.Relation

data class EventListRelations(

    @Embedded
    var eventListEntity: EventListEntity = EventListEntity(),

    @Relation(
        parentColumn = "event_list_id",
        entityColumn = "summary_event_list_id",
        entity = SummaryEntity::class
    )
    var eventListSummary: List<SummaryEntity> = mutableListOf()

)
