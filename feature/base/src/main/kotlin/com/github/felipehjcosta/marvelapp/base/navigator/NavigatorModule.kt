package com.github.felipehjcosta.marvelapp.base.navigator

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigatorModule {

    @Singleton
    @Provides
    fun providesAppNavigator(): AppNavigator = UrlBasedAppNavigator()
}