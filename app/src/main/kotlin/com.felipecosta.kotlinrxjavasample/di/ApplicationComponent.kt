package com.felipecosta.kotlinrxjavasample.di


import android.app.Application
import com.felipecosta.kotlinrxjavasample.DemoApplication
import com.felipecosta.kotlinrxjavasample.modules.listing.di.DetailActivityBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.listing.di.ListingFragmentBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        AndroidInjectionModule::class,
        ListingFragmentBuilderModule::class,
        DetailActivityBuilderModule::class
))
interface ApplicationComponent {
    fun inject(demoApplication: DemoApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}