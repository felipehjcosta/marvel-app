package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.*

@Entity(
        tableName = "series_list",
        indices = [Index(value = ["series_list_character_id"], name = "series_list_character_index")],
        foreignKeys = [ForeignKey(
                entity = CharacterEntity::class,
                parentColumns = ["id"],
                childColumns = ["series_list_character_id"])]
)
data class SeriesListEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "series_list_id")
        var id: Long = 0L,

        @ColumnInfo(name = "series_list_available")
        var available: Int = 0,

        @ColumnInfo(name = "series_list_returned")
        var returned: Int = 0,

        @ColumnInfo(name = "series_list_collection_uri")
        var collectionURI: String = "",

        @ColumnInfo(name = "series_list_character_id")
        var characterId: Long = 0L
)
