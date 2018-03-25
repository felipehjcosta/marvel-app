package com.github.felipehjcosta.marvelapp.base.rx

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

infix operator fun CompositeDisposable.plusAssign(disposable: Disposable): Unit {
    this.add(disposable)
}