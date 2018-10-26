package com.github.felipehjcosta.marvelapp.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
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