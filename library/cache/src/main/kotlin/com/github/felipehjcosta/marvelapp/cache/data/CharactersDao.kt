package com.github.felipehjcosta.marvelapp.cache.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class CharactersDao {

    @Query(
        """
        SELECT * FROM character, thumbnail, comic_list, story_list, event_list, series_list
        WHERE id = :id
        AND thumbnail.thumbnail_character_id = character.id
        AND comic_list.comic_list_character_id = character.id
        AND story_list.story_list_character_id = character.id
        AND event_list.event_list_character_id = character.id
        AND series_list.series_list_character_id = character.id
        LIMIT 1
        """
    )
    abstract fun findById(id: Long): Maybe<CharacterRelations>

    @Query(
        """
        SELECT * FROM character, thumbnail, comic_list, story_list, event_list, series_list
        WHERE
        thumbnail.thumbnail_character_id = character.id
        AND comic_list.comic_list_character_id = character.id
        AND story_list.story_list_character_id = character.id
        AND event_list.event_list_character_id = character.id
        AND series_list.series_list_character_id = character.id
        """
    )
    abstract fun all(): Single<List<CharacterRelations>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(characterEntity: CharacterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(urlEntity: UrlEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(thumbnailEntity: ThumbnailEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(comicListEntity: ComicListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(storyListEntity: StoryListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(eventListEntity: EventListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(seriesListEntity: SeriesListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insert(summaryEntity: SummaryEntity): Long

    fun insert(characterRelations: CharacterRelations) {
        val characterId = insert(characterRelations.character)
        characterRelations.urls.forEach {
            it.characterId = characterRelations.character.id
            it.id = insert(it)
        }
        characterRelations.thumbnail.characterId = characterId
        characterRelations.thumbnail.id = insert(characterRelations.thumbnail)
        characterRelations.comicListRelations.apply {
            comicList.characterId = characterId
            comicList.id = insert(comicList)
            comicListSummary.forEach {
                it.comicListId = comicList.id
                it.id = insert(it)
            }
        }

        characterRelations.storyListRelations.apply {
            storyListEntity.characterId = characterId
            storyListEntity.id = insert(storyListEntity)
            storyListSummary.forEach {
                it.storyListId = storyListEntity.id
                it.id = insert(it)
            }
        }

        characterRelations.eventListRelations.apply {
            eventListEntity.characterId = characterId
            eventListEntity.id = insert(eventListEntity)
            eventListSummary.forEach {
                it.eventListId = eventListEntity.id
                it.id = insert(it)
            }
        }

        characterRelations.seriesListRelations.apply {
            seriesListEntity.characterId = characterId
            seriesListEntity.id = insert(seriesListEntity)
            seriesListSummary.forEach {
                it.seriesListId = seriesListEntity.id
                it.id = insert(it)
            }
        }

    }
}
