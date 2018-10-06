package com.github.felipehjcosta.marvelapp.base.di


import android.app.Application
import android.content.SharedPreferences
import com.github.felipehjcosta.marvelapp.base.DemoApplication
import com.github.felipehjcosta.marvelapp.base.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.network.BaseUrl
import com.github.felipehjcosta.marvelapp.base.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    UiModule::class,
    NetworkModule::class,
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent {
    fun inject(application: DemoApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseUrl(@BaseUrl baseUrl: String): Builder

        fun build(): ApplicationComponent
    }

    val dataRepository: DataRepository

    val sharedPreferences: SharedPreferences

    val imageLoader: ImageLoader
}