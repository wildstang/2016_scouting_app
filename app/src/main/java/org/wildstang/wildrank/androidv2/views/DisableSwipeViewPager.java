package org.wildstang.wildrank.androidv2.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DisableSwipeViewPager extends ViewPager {

    private boolean mSwipeEnabled = true;

    public DisableSwipeViewPager(Context context) {
        super(context);
    }

    public DisableSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeEnabled(boolean enabled) {
        mSwipeEnabled = enabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mSwipeEnabled) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mSwipeEnabled) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        if (!mSwipeEnabled) {
            return;
        }
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (!mSwipeEnabled) {
            return;
        }
        super.setCurrentItem(item, smoothScroll);
    }

    public void setCurrentItemOverrideSwipeDisabled(int item) {
        super.setCurrentItem(item);
    }
}
