package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.*

@Entity(
        tableName = "summary",
        indices = [
            Index(value = ["summary_comic_list_id"], name = "summary_comic_list_index"),
            Index(value = ["summary_story_list_id"], name = "summary_story_list_index"),
            Index(value = ["summary_event_list_id"], name = "summary_event_list_index"),
            Index(value = ["summary_series_list_id"], name = "summary_series_list_index")

        ],
        foreignKeys = [
            ForeignKey(
                    entity = ComicListEntity::class,
                    parentColumns = ["comic_list_id"],
                    childColumns = ["summary_comic_list_id"]),
            ForeignKey(
                    entity = StoryListEntity::class,
                    parentColumns = ["story_list_id"],
                    childColumns = ["summary_story_list_id"])
            ,
            ForeignKey(
                    entity = EventListEntity::class,
                    parentColumns = ["event_list_id"],
                    childColumns = ["summary_event_list_id"]),
            ForeignKey(
                    entity = SeriesListEntity::class,
                    parentColumns = ["series_list_id"],
                    childColumns = ["summary_series_list_id"])
        ]
)
data class SummaryEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "summary_id")
        var id: Long = 0L,

        @ColumnInfo(name = "resource_uri")
        var resourceURI: String = "",

        var name: String = "",

        var type: String = "",

        @ColumnInfo(name = "summary_comic_list_id")
        var comicListId: Long? = null,

        @ColumnInfo(name = "summary_story_list_id")
        var storyListId: Long? = null,

        @ColumnInfo(name = "summary_event_list_id")
        var eventListId: Long? = null,

        @ColumnInfo(name = "summary_series_list_id")
        var seriesListId: Long? = null
)