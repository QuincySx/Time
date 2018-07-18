package com.smallraw.time.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Behavior extends CoordinatorLayout.Behavior<ViewPager> {
    private Context mContext;
    private float mTitleMaxY = 0;
    private float mTitleMinY = 0;

    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ViewPager child, View dependency) {
        Log.e("====", child.getClass().getSimpleName() + "     " + dependency.getClass().getSimpleName());
        boolean dependsOn = dependency instanceof ConstraintLayout;
        if (dependsOn) {
            mTitleMaxY = dependency.getMeasuredHeight();
        }
        mTitleMinY = parent.getChildAt(1).getMeasuredHeight();
        return dependsOn;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, ViewPager child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int parentMeasuredHeight = parent.getMeasuredHeight();

        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (parentMeasuredHeight - mTitleMinY), View.MeasureSpec.EXACTLY);
        child.measure(parentWidthMeasureSpec, heightMeasureSpec);
        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ViewPager child, int layoutDirection) {
        int[] childIndex = new int[]{0, 2};
        int totalHeight = 0;
        for (int i : childIndex) {
            View childAt = parent.getChildAt(i);
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredWidth = childAt.getMeasuredWidth();
//            Log.e("==onLayoutChild==", 0 + "  " + totalHeight + "  " + measuredWidth + "  " + totalHeight + "  " + measuredHeight);
            childAt.layout(0, totalHeight, measuredWidth, totalHeight += measuredHeight);
        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        Log.e("==", "onStartNestedScroll");
        return (axes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        Log.e("==", "onNestedPreScroll  y:" + y + "   dy:" + dy);
        float y = child.getY();
        //dy > 0 往上滑动；dy < 0 往下滑动；
        if (dy > 0) {
            if (y > mTitleMinY) {
                float scrollY = dy;
                if (y - dy < mTitleMinY) {
                    scrollY = y - mTitleMinY;
                }
                View childAt = coordinatorLayout.getChildAt(0);
                childAt.setY(childAt.getY() - scrollY);
                child.setY(y - scrollY);
                consumed[1] = (int) scrollY;
                scrollHeadView(Math.abs(child.getY() - mTitleMaxY), coordinatorLayout.getChildAt(0), coordinatorLayout.getChildAt(1));
            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ViewPager child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        Log.e("==", "onNestedScroll");
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        float y = child.getY();
        if (dyUnconsumed < 0) {
            if (y <= mTitleMaxY) {
                float scrollY = dyUnconsumed;
                if (y - dyUnconsumed > mTitleMaxY) {
                    scrollY = y - mTitleMaxY;
                }
                View childAt = coordinatorLayout.getChildAt(0);
                childAt.setY(childAt.getY() - scrollY);
                child.setY(y - scrollY);
                scrollHeadView(Math.abs(child.getY() - mTitleMaxY), coordinatorLayout.getChildAt(0), coordinatorLayout.getChildAt(1));
            }
        }
    }

    private void scrollHeadView(float currentTitleY, View maxView, View minView) {
        float totalProgress = 1 - currentTitleY / (mTitleMaxY - mTitleMinY);
        if (totalProgress == Float.POSITIVE_INFINITY || totalProgress == Float.NEGATIVE_INFINITY) {
            totalProgress = 1;
        }

        float offsetProgress = 0.3F;
        if (totalProgress < 1 - offsetProgress) {
//            float maxProgress = 1 - offsetProgress;
            float v = totalProgress / (1 - offsetProgress);

//            Log.e("===", "   totalProgress:" + totalProgress + "   Progress:" + (1 - offsetProgress) + "   v:" + v);
            maxView.setAlpha(v);
        } else {
            maxView.setAlpha(1f);
        }

        if (totalProgress < offsetProgress) {
//            float maxProgress = 1 - offsetProgress;
            float v = (offsetProgress - totalProgress) / offsetProgress;

            Log.e("===", "   totalProgress:" + totalProgress + "   Progress:" + (offsetProgress) + "   v:" + v);
            minView.setAlpha(v);
        } else {
            minView.setAlpha(0f);
        }
//        Log.e("==title==", " y:" + totalProgress);
    }
}
