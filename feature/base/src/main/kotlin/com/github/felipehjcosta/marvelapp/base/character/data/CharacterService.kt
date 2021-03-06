package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.CharacterDataWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("v1/public/characters")
    fun listCharacters(@Query("limit") limit: Int,
                       @Query("offset") offset: Int,
                       @Query("ts") timestamp: String,
                       @Query("apikey") apikey: String,
                       @Query("hash") hashSignature: String): Single<CharacterDataWrapper>

    @GET("v1/public/characters/{characterid}")
    fun getCharacterWithId(@Path("characterid") characterId: Int,
                           @Query("ts") timestamp: String,
                           @Query("apikey") apikey: String,
                           @Query("hash") hashSignature: String): Single<CharacterDataWrapper>

}
