package com.github.felipehjcosta.appdetail.di

import com.github.felipehjcosta.appdetail.view.DetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailActivityBuilderModule {

    @DetailScope
    @ContributesAndroidInjector(modules = [DetailModule::class, DetailBindingsModule::class])
    abstract fun detailActivity(): DetailActivity

}