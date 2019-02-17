package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.*

@Entity(
    tableName = "url",
    indices = [Index(value = ["url_character_id"], name = "url_character_index")],
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["url_character_id"]
    )]
)
data class UrlEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "url_id")
    var id: Long = 0L,

    var url: String = "",

    var type: String = "",

    @ColumnInfo(name = "url_character_id")
    var characterId: Long = 0L
)
