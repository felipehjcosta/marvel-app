package com.github.felipehjcosta.marvelapp.base.modules.wiki.di

import com.github.felipehjcosta.marvelapp.base.modules.wiki.view.WikiFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WikiFragmentBuilderModule {

    @WikiScope
    @ContributesAndroidInjector(modules = [WikiModule::class])
    abstract fun wikiFragment(): WikiFragment
}