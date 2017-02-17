package com.example.checkableheart.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.example.checkableheart.R;
/**
 * Animations based in Nick Butcher's repository https://github.com/nickbutcher/plaid
 *
 *
 * A {@link Checkable} {@link FloatingActionButton} which can be offset vertically.
 */
public class HeartFab extends FloatingActionButton implements Checkable, View.OnClickListener {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean isChecked = false;
    private int minOffset;
    private OnCheckedChangeListener checkedChangeListener;

    public HeartFab(Context context) {
        super(context, null);
        init();
    }

    public HeartFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.heart_fab_background)));
        setImageResource(R.drawable.asl_fab_heart);
        setScaleType(ScaleType.CENTER);
        setSize(SIZE_NORMAL);
        setOnClickListener(this);
    }

    public void setOffset(int offset) {
        if (offset != getTranslationY()) {
            offset = Math.max(minOffset, offset);
            setTranslationY(offset);
        }
    }

    public void setMinOffset(int minOffset) {
        this.minOffset = minOffset;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            refreshDrawableState();
        }
        if (checkedChangeListener != null) {
            checkedChangeListener.onCheckedChanged(this, this.isChecked);
        }
    }

    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void onClick(View view) {
        toggle();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.checkedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(HeartFab heartFab, boolean checked);
    }
}