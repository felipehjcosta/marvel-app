package com.github.felipehjcosta.marvelapp.cache.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey()
    var id: Long = 0L,

    var name: String = "",

    var description: String = "",

    @ColumnInfo(name = "resource_uri")
    var resourceURI: String = ""
)
