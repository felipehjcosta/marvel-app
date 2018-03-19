package com.github.felipehjcosta.appdetail.di

import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.util.AppInjector
import com.github.felipehjcosta.appdetail.view.DetailActivity
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
    AppInjector.register(DetailActivity::class.java) { applicationComponent ->
        DaggerDetailComponent.builder().plus(applicationComponent)
    }
}