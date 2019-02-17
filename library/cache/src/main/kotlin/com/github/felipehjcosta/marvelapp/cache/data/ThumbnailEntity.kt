package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.*

@Entity(
    tableName = "thumbnail",
    indices = [Index(value = ["thumbnail_character_id"], name = "thumbnail_character_index")],
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["thumbnail_character_id"]
    )]
)
data class ThumbnailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "thumbnail_id")
    var id: Long = 0L,

    var path: String = "",

    var extension: String = "",

    @ColumnInfo(name = "thumbnail_character_id")
    var characterId: Long = 0L
)
