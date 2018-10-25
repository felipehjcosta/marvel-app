package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.*

@Entity(
        tableName = "event_list",
        indices = [Index(value = ["event_list_character_id"], name = "event_list_character_index")],
        foreignKeys = [ForeignKey(
                entity = CharacterEntity::class,
                parentColumns = ["id"],
                childColumns = ["event_list_character_id"])]
)
data class EventListEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "event_list_id")
        var id: Long = 0L,

        @ColumnInfo(name = "event_list_available")
        var available: Int = 0,

        @ColumnInfo(name = "event_list_returned")
        var returned: Int = 0,

        @ColumnInfo(name = "event_list_collection_uri")
        var collectionURI: String = "",

        @ColumnInfo(name = "event_list_character_id")
        var characterId: Long = 0L
)
