package com.felipecosta.kotlinrxjavasample.modules.listing.di

import com.felipecosta.kotlinrxjavasample.modules.listing.view.ListingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListingFragmentBuilderModule {

    @ListingScope
    @ContributesAndroidInjector(modules = arrayOf(ListingModule::class))
    abstract fun listingFragment(): ListingFragment

}