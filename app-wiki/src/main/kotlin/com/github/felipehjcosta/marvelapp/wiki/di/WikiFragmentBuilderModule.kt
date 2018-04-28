package com.github.felipehjcosta.marvelapp.wiki.di

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WikiFragmentBuilderModule {

    @WikiScope
    @ContributesAndroidInjector(modules = [WikiModule::class])
    abstract fun wikiFragment(): WikiFragmentBuilderModule
}