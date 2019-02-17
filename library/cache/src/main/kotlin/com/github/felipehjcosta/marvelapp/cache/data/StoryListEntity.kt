package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.*

@Entity(
    tableName = "story_list",
    indices = [Index(value = ["story_list_character_id"], name = "story_list_character_index")],
    foreignKeys = [ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["story_list_character_id"]
    )]
)
data class StoryListEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "story_list_id")
    var id: Long = 0L,

    @ColumnInfo(name = "story_list_available")
    var available: Int = 0,

    @ColumnInfo(name = "story_list_returned")
    var returned: Int = 0,

    @ColumnInfo(name = "story_list_collection_uri")
    var collectionURI: String = "",

    @ColumnInfo(name = "story_list_character_id")
    var characterId: Long = 0L
)
