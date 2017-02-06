package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.detail.di.DetailComponent
import com.felipecosta.kotlinrxjavasample.modules.listing.di.ListingComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(ListingComponent::class, DetailComponent::class))
abstract class ApplicationBinders {

    @Binds
    @IntoMap
    @SubcomponentKey(ListingComponent::class)
    abstract fun listingComponentBuilder(impl: ListingComponent.Builder): SubcomponentBuilder<*>

    @Binds
    @IntoMap
    @SubcomponentKey(DetailComponent::class)
    abstract fun detailComponentBuilder(impl: DetailComponent.Builder): SubcomponentBuilder<*>
}