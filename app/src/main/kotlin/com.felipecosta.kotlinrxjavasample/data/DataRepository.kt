package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.BuildConfig
import com.felipecosta.kotlinrxjavasample.data.pojo.CharacterDataWrapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.NoSuchAlgorithmException
import java.util.*


class DataRepository {
    val PORT = "80"
    val BASE_URL = "http://gateway.marvel.com" + ":" + PORT
    val timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L
    var characterService: CharacterService

    constructor() {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        characterService = retrofit.create(CharacterService::class.java)
    }

    fun getHash(): String {
        return md5(timestamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY)
    }

    fun getCharacterList(offset: Int, limit: Int): Observable<CharacterDataWrapper> {
        return characterService.listCharacters(limit,
                offset,
                timestamp.toString(),
                BuildConfig.MARVEL_PUBLIC_KEY,
                getHash())
    }

    fun getCharacter(characterId: Int): Observable<CharacterDataWrapper> {
        return characterService.getCharacterWithId(
                characterId,
                timestamp.toString(),
                BuildConfig.MARVEL_PUBLIC_KEY,
                getHash())
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest.getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}
