package com.github.felipehjcosta.marvelapp.base.modules.listing.di

import com.github.felipehjcosta.marvelapp.base.modules.listing.view.CharacterListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CharacterListingFragmentBuilderModule {

    @ListingScope
    @ContributesAndroidInjector(modules = [ListingModule::class])
    abstract fun characterListingFragment(): CharacterListingFragment

}