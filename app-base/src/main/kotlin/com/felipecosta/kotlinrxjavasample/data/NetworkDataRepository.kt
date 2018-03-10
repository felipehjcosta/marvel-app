package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.BuildConfig
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable
import java.security.NoSuchAlgorithmException
import java.util.*


class NetworkDataRepository(private val characterService: CharacterService) : DataRepository {

    val timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L

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
                    characterDataWrapper.characterDataContainer.characters
                }
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        return characterService.getCharacterWithId(
                characterId,
                timestamp.toString(),
                BuildConfig.MARVEL_PUBLIC_KEY,
                getHash())
                .map { characterDataWrapper ->
                    characterDataWrapper.characterDataContainer.characters[0]
                }
    }

    fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest.getInstance("MD5")
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