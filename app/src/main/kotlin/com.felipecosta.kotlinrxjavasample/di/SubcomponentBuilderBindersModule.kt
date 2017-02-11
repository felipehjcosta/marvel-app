package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.modules.detail.di.DetailComponent
import com.felipecosta.kotlinrxjavasample.modules.listing.di.ListingComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(ListingComponent::class, DetailComponent::class))
abstract class SubcomponentBuilderBindersModule {

    @Binds
    @IntoMap
    @SubcomponentKey(ListingComponent.Builder::class)
    abstract fun listingComponentBuilder(impl: ListingComponent.Builder): SubcomponentBuilder<*>

    @Binds
    @IntoMap
    @SubcomponentKey(DetailComponent.Builder::class)
    abstract fun detailComponentBuilder(impl: DetailComponent.Builder): SubcomponentBuilder<*>
}