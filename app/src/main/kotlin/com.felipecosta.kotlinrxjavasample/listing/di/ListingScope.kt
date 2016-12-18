package com.felipecosta.kotlinrxjavasample.listing.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ListingScope