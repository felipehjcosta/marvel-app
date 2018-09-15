package com.github.felipehjcosta.marvelapp.wiki.main

import android.view.View
import com.agoda.kakao.KRecyclerItem
import com.agoda.kakao.KRecyclerView
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import com.github.felipehjcosta.marvelapp.wiki.R
import org.hamcrest.Matcher

class HighlightedCharactersScreen : Screen<HighlightedCharactersScreen>() {

    val recyclerView = KRecyclerView({
        withId(R.id.highlighted_characters_recycler_view)
    }, {
        itemType(::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val title: KTextView = KTextView(parent) { withId(R.id.title) }
    }
}