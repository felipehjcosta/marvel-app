package com.felipecosta.kotlinrxjavasample.modules.wiki.view

import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View


class GravitySnapHelper(private var gravity: Int) : LinearSnapHelper() {

    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null
    private var isSupportRtL: Boolean = false

    init {
        if (this.gravity == Gravity.LEFT) {
            this.gravity = Gravity.START
        } else if (this.gravity == Gravity.RIGHT) {
            this.gravity = Gravity.END
        }
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            if ((gravity == Gravity.START || gravity == Gravity.END)
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                isSupportRtL = recyclerView.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
            }
        }
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray {
        val out = IntArray(2)

        if (layoutManager.canScrollHorizontally()) {
            if (gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
            } else { // END
                out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager))
            }
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager))
            }
        } else {
            out[1] = 0
        }
        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager is LinearLayoutManager) {
            when (gravity) {
                Gravity.START -> return findStartView(layoutManager, getHorizontalHelper(layoutManager))
                Gravity.TOP -> return findStartView(layoutManager, getVerticalHelper(layoutManager))
                Gravity.END -> return findEndView(layoutManager, getHorizontalHelper(layoutManager))
                Gravity.BOTTOM -> return findEndView(layoutManager, getVerticalHelper(layoutManager))
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        if (isSupportRtL) {
            return distanceToEnd(targetView, helper)
        }
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    private fun distanceToEnd(targetView: View, helper: OrientationHelper): Int {
        if (isSupportRtL) {
            return helper.getDecoratedStart(targetView) - helper.startAfterPadding
        }
        return helper.getDecoratedEnd(targetView) - helper.endAfterPadding
    }

    private fun findStartView(layoutManager: RecyclerView.LayoutManager,
                              helper: OrientationHelper): View? {

        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()

            if (firstChild == RecyclerView.NO_POSITION) {
                return null
            }

            val child = layoutManager.findViewByPosition(firstChild)

            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 && helper.getDecoratedEnd(child) > 0) {
                return child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                    return null
                } else {
                    return layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun findEndView(layoutManager: RecyclerView.LayoutManager,
                            helper: OrientationHelper): View? {

        if (layoutManager is LinearLayoutManager) {
            val lastChild = layoutManager.findLastVisibleItemPosition()

            if (lastChild == RecyclerView.NO_POSITION) {
                return null
            }

            val child = layoutManager.findViewByPosition(lastChild)

            if (helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2 <= helper.totalSpace) {
                return child
            } else {
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    return null
                } else {
                    return layoutManager.findViewByPosition(lastChild - 1)
                }
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager) = verticalHelper ?: OrientationHelper
            .createVerticalHelper(layoutManager).apply { verticalHelper = this }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager) = horizontalHelper ?: OrientationHelper
            .createHorizontalHelper(layoutManager).apply { horizontalHelper = this }

}