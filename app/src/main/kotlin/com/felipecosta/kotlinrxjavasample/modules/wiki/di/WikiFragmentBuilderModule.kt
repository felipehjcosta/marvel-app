package com.felipecosta.kotlinrxjavasample.modules.wiki.di

import com.felipecosta.kotlinrxjavasample.modules.wiki.view.WikiFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WikiFragmentBuilderModule {

    @WikiScope
    @ContributesAndroidInjector(modules = [WikiModule::class])
    abstract fun wikiFragment(): WikiFragment
}