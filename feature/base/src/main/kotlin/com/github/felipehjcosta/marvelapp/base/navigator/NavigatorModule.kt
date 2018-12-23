package com.github.felipehjcosta.marvelapp.base.navigator

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class NavigatorModule {

    @Singleton
    @Binds
    abstract fun providesAppNavigator(urlBasedAppNavigator: UrlBasedAppNavigator): AppNavigator
}
