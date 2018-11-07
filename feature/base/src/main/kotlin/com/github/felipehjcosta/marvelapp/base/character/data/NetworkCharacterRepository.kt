package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.BuildConfig.MARVEL_PRIVATE_KEY
import com.github.felipehjcosta.marvelapp.base.BuildConfig.MARVEL_PUBLIC_KEY
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable
import java.util.*


class NetworkCharacterRepository(private val characterService: CharacterService) : CharacterRepository {

    private val timestamp: String
        get() {
            return (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        }

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        val localTimestamp = timestamp
        val hashSignature = generateHash(localTimestamp)
        return characterService.listCharacters(limit,
                offset,
                localTimestamp,
                MARVEL_PUBLIC_KEY,
                hashSignature)
                .map { characterDataWrapper ->
                    characterDataWrapper.characterDataContainer.characters
                }
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        val localTimestamp = timestamp
        val hashSignature = generateHash(localTimestamp)
        return characterService.getCharacterWithId(
                characterId,
                localTimestamp,
                MARVEL_PUBLIC_KEY,
                hashSignature)
                .map { characterDataWrapper ->
                    characterDataWrapper.characterDataContainer.characters[0]
                }
    }

    private fun generateHash(timestamp: String): String {
        return (timestamp + MARVEL_PRIVATE_KEY + MARVEL_PUBLIC_KEY).toMD5()
    }

    private fun String.toMD5(): String {
        val md = java.security.MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }

}