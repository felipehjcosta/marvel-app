package com.felipecosta.kotlinrxjavasample.samples


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.felipecosta.kotlinrxjavasample.R

class Sample1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_sample1, container, false)
    }

    companion object {
        fun newInstance() = Sample1Fragment()
    }

}
