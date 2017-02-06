package com.felipecosta.kotlinrxjavasample.modules.listing.di

import com.felipecosta.kotlinrxjavasample.di.SubcomponentBuilder
import com.felipecosta.kotlinrxjavasample.modules.listing.view.ListingFragment
import dagger.Subcomponent

@ListingScope
@Subcomponent(modules = arrayOf(ListingModule::class))
interface ListingComponent {
    fun inject(listingFragment: ListingFragment)

    @Subcomponent.Builder
    interface Builder : SubcomponentBuilder<ListingComponent>
}