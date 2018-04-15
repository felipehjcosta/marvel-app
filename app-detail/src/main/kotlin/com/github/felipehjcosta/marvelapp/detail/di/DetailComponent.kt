package com.github.felipehjcosta.marvelapp.detail.di

import com.github.felipehjcosta.marvelapp.detail.view.DetailActivity
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.util.AppInjector
import dagger.Component
import dagger.android.AndroidInjector

@DetailScope
@Component(
        modules = [DetailModule::class, DetailBindingsModule::class],
        dependencies = [ApplicationComponent::class]
)
interface DetailComponent : AndroidInjector<DetailActivity> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DetailActivity>() {
        abstract fun plus(component: ApplicationComponent): Builder
    }

}

fun setupDependencyInjection() {
    AppInjector.registerActivityBuilder(DetailActivity::class.java) { applicationComponent ->
        DaggerDetailComponent.builder().plus(applicationComponent)
    }
}