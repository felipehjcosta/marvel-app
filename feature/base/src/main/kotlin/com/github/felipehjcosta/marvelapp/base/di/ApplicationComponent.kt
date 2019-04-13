package com.github.felipehjcosta.marvelapp.base.di


import android.app.Application
import android.content.SharedPreferences
import com.github.felipehjcosta.marvelapp.base.MarvelAppApplication
import com.github.felipehjcosta.marvelapp.base.cache.CacheModule
import com.github.felipehjcosta.marvelapp.base.character.CharacterModule
import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.navigator.AppNavigator
import com.github.felipehjcosta.marvelapp.base.navigator.NavigatorModule
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
    NetworkModule::class,
    CacheModule::class,
    AppModule::class,
    UiModule::class,
    NavigatorModule::class,
    CharacterModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface ApplicationComponent {
    fun inject(application: MarvelAppApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseUrl(@BaseUrl baseUrl: String): Builder

        fun build(): ApplicationComponent
    }

    val characterRepository: CharacterRepository

    val sharedPreferences: SharedPreferences

    val imageLoader: ImageLoader

    val appNavigator: AppNavigator
}
