package com.felipecosta.kotlinrxjavasample.modules.listing.di

import com.felipecosta.kotlinrxjavasample.modules.listing.view.CharacterListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CharacterListingFragmentBuilderModule {

    @ListingScope
    @ContributesAndroidInjector(modules = [ListingModule::class])
    abstract fun characterListingFragment(): CharacterListingFragment

}