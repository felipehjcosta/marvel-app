package com.felipecosta.kotlinrxjavasample.detail.di

import com.felipecosta.kotlinrxjavasample.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.di.SubcomponentBuilder
import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = arrayOf(DetailModule::class))
interface DetailComponent {
    fun inject(detailActivity: DetailActivity)

    @Subcomponent.Builder
    interface Builder : SubcomponentBuilder<DetailComponent> {
        fun detailModule(detailModule: DetailModule): Builder
    }
}