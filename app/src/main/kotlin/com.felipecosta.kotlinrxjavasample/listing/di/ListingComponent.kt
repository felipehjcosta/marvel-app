package com.felipecosta.kotlinrxjavasample.listing.di

import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.listing.view.ListingFragment
import dagger.Component

@ListingScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ListingModule::class))
interface ListingComponent {
    fun inject(listingFragment: ListingFragment)
}