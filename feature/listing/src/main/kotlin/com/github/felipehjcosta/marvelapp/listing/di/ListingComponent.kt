package com.github.felipehjcosta.marvelapp.listing.di

import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.util.AppInjector
import com.github.felipehjcosta.marvelapp.listing.view.CharacterListingFragment
import dagger.Component
import dagger.android.AndroidInjector

@ListingScope
@Component(
        modules = [ListingModule::class],
        dependencies = [ApplicationComponent::class]
)
interface ListingComponent : AndroidInjector<CharacterListingFragment> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CharacterListingFragment>() {
        abstract fun plus(component: ApplicationComponent): Builder
    }

}

fun setupDependencyInjection() {
    AppInjector.registerFragmentBuilder(CharacterListingFragment::class.java) { applicationComponent ->
        DaggerListingComponent.builder().plus(applicationComponent)
    }
}
