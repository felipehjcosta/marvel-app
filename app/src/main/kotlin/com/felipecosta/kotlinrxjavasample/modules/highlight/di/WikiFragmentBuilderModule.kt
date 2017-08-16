package com.felipecosta.kotlinrxjavasample.modules.highlight.di

import com.felipecosta.kotlinrxjavasample.modules.detail.di.WikiScope
import com.felipecosta.kotlinrxjavasample.modules.highlight.view.WikiFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WikiFragmentBuilderModule {

    @WikiScope
    @ContributesAndroidInjector(modules = arrayOf(WikiModule::class))
    abstract fun wikiFragment(): WikiFragment
}