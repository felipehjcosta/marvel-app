package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.util.AppInjector
import com.github.felipehjcosta.marvelapp.wiki.view.WikiFragment
import dagger.Component
import dagger.android.AndroidInjector

@WikiScope
@Component(
    modules = [WikiModule::class],
    dependencies = [ApplicationComponent::class]
)
interface WikiComponent : AndroidInjector<WikiFragment> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WikiFragment>() {
        abstract fun plus(component: ApplicationComponent): Builder
    }
}

fun setupDependencyInjection() {
    AppInjector.registerFragmentBuilder(WikiFragment::class.java) { applicationComponent ->
        DaggerWikiComponent.builder().plus(applicationComponent)
    }
}
