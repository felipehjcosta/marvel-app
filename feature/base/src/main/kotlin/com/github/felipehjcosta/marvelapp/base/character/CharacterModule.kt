package com.github.felipehjcosta.marvelapp.base.character

import com.github.felipehjcosta.marvelapp.base.character.data.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CharacterModule {

    @Singleton
    @Provides
    fun providesDataRepository(retrofit: Retrofit, cache: SimpleDiskCache): DataRepository {
        val characterService = retrofit.create(CharacterService::class.java)
        return CacheDataRepository(NetworkDataRepository(characterService), cache)
    }
}