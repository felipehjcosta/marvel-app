package com.github.felipehjcosta.marvelapp.base.character

import com.github.felipehjcosta.marvelapp.base.character.data.CacheCharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.CharacterService
import com.github.felipehjcosta.marvelapp.base.character.data.NetworkCharacterRepository
import com.github.felipehjcosta.marvelapp.cache.CacheDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CharacterModule {

    @Singleton
    @Provides
    fun providesDataRepository(
        retrofit: Retrofit,
        cacheDatabase: CacheDatabase
    ): CharacterRepository {
        val characterService = retrofit.create(CharacterService::class.java)
        val charactersDao = cacheDatabase.charactersDao()
        return CacheCharacterRepository(NetworkCharacterRepository(characterService), charactersDao)
    }
}
