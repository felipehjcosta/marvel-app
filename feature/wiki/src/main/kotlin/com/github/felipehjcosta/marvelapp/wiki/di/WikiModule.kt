package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModelInputOutput
import dagger.Binds
import dagger.Module

@Module
abstract class WikiModule {

    @Binds
    abstract fun providesViewModel(viewModel: HighlightedCharactersViewModelInputOutput): HighlightedCharactersViewModel

}
