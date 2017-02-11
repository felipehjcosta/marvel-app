package com.felipecosta.kotlinrxjavasample.modules.detail.di

import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
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