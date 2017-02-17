package com.felipecosta.kotlinrxjavasample.data

import android.content.Context
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

class DiskDataRepository(val context: Context) : DataRepository {

    override fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>> {
        return Observable.just(null)
    }

    override fun getCharacter(characterId: Int): Observable<Character> {
        return Observable.just(null)
    }

    override fun favoriteCharacter(characterId: Int): Observable<Boolean> {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(context.getString(R.string.saved_favorite_characters), characterId)
        editor.apply()
        return Observable.just(true)
    }

}
