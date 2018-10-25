package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class EventListRelations(

        @Embedded
        var eventListEntity: EventListEntity = EventListEntity(),

        @Relation(
                parentColumn = "event_list_id",
                entityColumn = "summary_event_list_id",
                entity = SummaryEntity::class)
        var eventListSummary: List<SummaryEntity> = mutableListOf()

)