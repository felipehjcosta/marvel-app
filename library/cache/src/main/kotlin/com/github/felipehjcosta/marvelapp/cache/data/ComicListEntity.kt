package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.*

@Entity(
    tableName = "comic_list",
    indices = [Index(value = ["comic_list_character_id"], name = "comic_list_character_index")],
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["comic_list_character_id"]
    )]
)
data class ComicListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comic_list_id")
    var id: Long = 0L,

    @ColumnInfo(name = "comic_list_available")
    var available: Int = 0,

    @ColumnInfo(name = "comic_list_returned")
    var returned: Int = 0,

    @ColumnInfo(name = "comic_list_collection_uri")
    var collectionURI: String = "",

    @ColumnInfo(name = "comic_list_character_id")
    var characterId: Long = 0L
)
