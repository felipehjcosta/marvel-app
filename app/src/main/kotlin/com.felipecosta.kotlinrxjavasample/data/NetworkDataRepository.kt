package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.BuildConfig
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.NoSuchAlgorithmException
import java.util.*


class NetworkDataRepository : DataRepository {

    val PORT = "80"
    val BASE_URL = "http://gateway.marvel.com" + ":" + PORT
    val timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L
    val characterService: CharacterService

    init {
        val httpClient = OkHttpClient.Builder()
        setLoggingInterceptorsForDebug(httpClient)
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        characterService = retrofit.create(CharacterService::class.java)
    }

    private fun setLoggingInterceptorsForDebug(httpClient: OkHttpClient.Builder) {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
    }

    fun getHash(): String {
        return md5(timestamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY)
    }

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        return characterService.listCharacters(limit,
                offset,
                timestamp.toString(),
                BuildConfig.MARVEL_PUBLIC_KEY,
                getHash())
                .map { characterDataWrapper ->
                    characterDataWrapper.characterDataContainer?.characters
                }
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        return characterService.getCharacterWithId(
                characterId,
                timestamp.toString(),
                BuildConfig.MARVEL_PUBLIC_KEY,
                getHash())
                .map { characterDataWrapper ->
                    characterDataWrapper.characterDataContainer?.characters?.get(0)
                }
    }

    override fun favoriteCharacter(characterId: Int): Observable<Boolean> {
        return Observable.just(false)
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