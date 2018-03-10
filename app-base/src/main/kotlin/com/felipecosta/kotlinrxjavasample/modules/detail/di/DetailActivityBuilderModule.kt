package com.felipecosta.kotlinrxjavasample.modules.detail.di

import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailActivityBuilderModule {

    @DetailScope
    @ContributesAndroidInjector(modules = [DetailModule::class, DetailBindingsModule::class])
    abstract fun detailActivity(): DetailActivity

}