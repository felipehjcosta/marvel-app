package com.github.felipehjcosta.marvelapp.base.character

import com.github.felipehjcosta.marvelapp.base.character.data.*
import com.github.felipehjcosta.marvelapp.cache.CacheDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CharacterModule {

    @Singleton
    @Provides
    fun providesDataRepository(retrofit: Retrofit, cacheDatabase: CacheDatabase): DataRepository {
        val characterService = retrofit.create(CharacterService::class.java)
        val charactersDao = cacheDatabase.charactersDao()
        return CacheDataRepository(NetworkDataRepository(characterService), charactersDao)
    }
}