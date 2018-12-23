package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
        @PrimaryKey()
        var id: Long = 0L,

        var name: String = "",

        var description: String = "",

        @ColumnInfo(name = "resource_uri")
        var resourceURI: String = ""
)
