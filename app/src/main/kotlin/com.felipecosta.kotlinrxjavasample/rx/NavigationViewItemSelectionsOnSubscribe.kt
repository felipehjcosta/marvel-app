package com.felipecosta.kotlinrxjavasample.rx

import android.support.design.widget.NavigationView
import android.view.MenuItem
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.MainThreadDisposable

internal class NavigationViewItemSelectionsOnSubscribe(val view: NavigationView) : ObservableOnSubscribe<MenuItem> {

    override fun subscribe(emitter: ObservableEmitter<MenuItem>?) {
        MainThreadDisposable.verifyMainThread()

        val listener = NavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (!(emitter?.isDisposed ?: false)) {
                emitter?.onNext(menuItem)
            }
            true
        }

        emitter?.setDisposable(object : MainThreadDisposable() {
            override fun onDispose() {
                view.setNavigationItemSelectedListener(null)
            }
        })

        view.setNavigationItemSelectedListener(listener)

        // Emit initial checked item, if one can be found.
        val menu = view.menu
        val count = menu.size()
        for (i in 0 until count) {
            val item = menu.getItem(i)
            if (item.isChecked) {
                emitter?.onNext(item)
                break
            }
        }
    }
}