package com.felipecosta.kotlinrxjavasample.modules.highlight.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.felipecosta.kotlinrxjavasample.R


class WikiFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_wiki, container, false)
    }

    companion object {
        fun newInstance() = WikiFragment()
    }

}
