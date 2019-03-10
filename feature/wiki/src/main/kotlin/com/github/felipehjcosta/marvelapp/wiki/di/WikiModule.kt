package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.HighlightedCharactersViewModelInputOutput
import com.github.felipehjcosta.marvelapp.wiki.presentation.OthersCharactersViewModel
import com.github.felipehjcosta.marvelapp.wiki.presentation.OthersCharactersViewModelInputOutput
import dagger.Binds
import dagger.Module

@Module
abstract class WikiModule {

    @Binds
    abstract fun providesHighlightedCharactersViewModel(
        viewModel: HighlightedCharactersViewModelInputOutput
    ): HighlightedCharactersViewModel

    @Binds
    abstract fun providesOthersCharactersViewModel(
        viewModel: OthersCharactersViewModelInputOutput
    ): OthersCharactersViewModel
}
