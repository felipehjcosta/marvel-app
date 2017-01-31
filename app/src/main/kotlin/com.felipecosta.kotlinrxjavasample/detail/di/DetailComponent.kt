package com.felipecosta.kotlinrxjavasample.detail.di

import com.felipecosta.kotlinrxjavasample.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import dagger.Component

@DetailScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(DetailModule::class))
interface DetailComponent {
    fun inject(detailActivity: DetailActivity)
}