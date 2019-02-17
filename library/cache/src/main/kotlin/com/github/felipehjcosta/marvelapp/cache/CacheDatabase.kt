package com.github.felipehjcosta.marvelapp.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.felipehjcosta.marvelapp.cache.data.*

@Database(
    entities = [
        CharacterEntity::class,
        UrlEntity::class,
        ThumbnailEntity::class,
        ComicListEntity::class,
        StoryListEntity::class,
        EventListEntity::class,
        SeriesListEntity::class,
        SummaryEntity::class
    ],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}

fun newInstance(context: Context): CacheDatabase {
    return Room.databaseBuilder(
        context,
        CacheDatabase::class.java,
        "cache_database.db"
    ).fallbackToDestructiveMigration()
        .build()
}
