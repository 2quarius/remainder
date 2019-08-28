package com.example.trail.Utility.Widget;

import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.FrameLayout;

public class ExpandableItemIndicator extends FrameLayout {
    static abstract class Impl {
        public abstract void onInit(Context context, AttributeSet attrs, int defStyleAttr, ExpandableItemIndicator thiz);

        public abstract void setExpandedState(boolean isExpanded, boolean animate);
    }

    private Impl mImpl;

    public ExpandableItemIndicator(Context context) {
        super(context);
        onInit(context, null, 0);
    }

    public ExpandableItemIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit(context, attrs, 0);
    }

    public ExpandableItemIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInit(context, attrs, defStyleAttr);
    }

    protected boolean shouldUseAnimatedIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        // NOTE: AnimatedVectorDrawableCompat works on API level 11+,
        // but I prefer to use it on API level 16+ only due to performance reason of
        // both hardware and Android platform.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    protected void onInit(Context context, AttributeSet attrs, int defStyleAttr) {
        if (shouldUseAnimatedIndicator(context, attrs, defStyleAttr)) {
            mImpl = new ExpandableItemIndicatorImplAnim();
        } else {
            mImpl = new ExpandableItemIndicatorImplNoAnim();
        }
        mImpl.onInit(context, attrs, defStyleAttr, this);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    public void setExpandedState(boolean isExpanded, boolean animate) {
        mImpl.setExpandedState(isExpanded, animate);
    }
}
