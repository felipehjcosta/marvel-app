package com.felipecosta.kotlinrxjavasample.di


import android.app.Application
import com.felipecosta.kotlinrxjavasample.DemoApplication
import com.felipecosta.kotlinrxjavasample.modules.highlight.di.WikiFragmentBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.listing.di.CharacterListingFragmentBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.listing.di.DetailActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        AndroidInjectionModule::class,
        CharacterListingFragmentBuilderModule::class,
        DetailActivityBuilderModule::class,
        WikiFragmentBuilderModule::class
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