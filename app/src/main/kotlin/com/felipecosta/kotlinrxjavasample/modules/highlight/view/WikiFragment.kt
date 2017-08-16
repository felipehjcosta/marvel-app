package com.felipecosta.kotlinrxjavasample.modules.highlight.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.highlight.presentation.HighlightCharacterViewModel
import com.felipecosta.kotlinrxjavasample.modules.highlight.presentation.OthersCharactersViewModel
import javax.inject.Inject


class WikiFragment : Fragment() {

    @Inject
    lateinit var highlightCharacterViewModel: HighlightCharacterViewModel

    @Inject
    lateinit var othersCharactersViewModel: OthersCharactersViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_wiki, container, false)
    }

    companion object {
        fun newInstance() = WikiFragment()
    }

}
