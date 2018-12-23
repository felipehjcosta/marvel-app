package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.BuildConfig.MARVEL_PRIVATE_KEY
import com.github.felipehjcosta.marvelapp.base.BuildConfig.MARVEL_PUBLIC_KEY
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Single
import java.util.*


class NetworkCharacterRepository(private val characterService: CharacterService) :
    CharacterRepository {

    companion object {
        private const val MILLISECONDS = 1000L
    }

    private val timestamp: String
        get() {
            return (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / MILLISECONDS).toString()
        }

    override fun getCharacterList(offset: Int, limit: Int): Single<List<Character>> {
        val localTimestamp = timestamp
        val hashSignature = generateHash(localTimestamp)
        return characterService.listCharacters(
            limit,
            offset,
            localTimestamp,
            MARVEL_PUBLIC_KEY,
            hashSignature
        ).map { characterDataWrapper ->
            characterDataWrapper.characterDataContainer.characters
        }
    }

    override fun getCharacter(characterId: Int): Single<Character> {
        val localTimestamp = timestamp
        val hashSignature = generateHash(localTimestamp)
        return characterService.getCharacterWithId(
            characterId,
            localTimestamp,
            MARVEL_PUBLIC_KEY,
            hashSignature
        ).map { characterDataWrapper ->
            characterDataWrapper.characterDataContainer.characters[0]
        }
    }

    private fun generateHash(timestamp: String): String {
        return (timestamp + MARVEL_PRIVATE_KEY + MARVEL_PUBLIC_KEY).toMD5()
    }

    private fun String.toMD5(): String {
        val md = java.security.MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") { String.format("%02x", it) }
    }
}
