package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class StoryListRelations(

        @Embedded
        var storyListEntity: StoryListEntity = StoryListEntity(),

        @Relation(
                parentColumn = "story_list_id",
                entityColumn = "summary_story_list_id",
                entity = SummaryEntity::class)
        var storyListSummary: List<SummaryEntity> = mutableListOf()

)
