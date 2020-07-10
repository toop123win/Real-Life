package com.local.project.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;


@SuppressWarnings("SuspiciousNameCombination")
public class LinearLayoutDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_HEIGHT = 1;
    private Drawable mDivider;
    private Builder mBuilder;

    private LinearLayoutDividerItemDecoration(Builder builder) {
        this.mBuilder = builder;
        if (builder.dividerDrawable == null) {
            mDivider = new ColorDrawable(Color.GRAY) {
                @Override
                public int getIntrinsicHeight() {
                    return mBuilder.dividerHeight;
                }

                @Override
                public int getIntrinsicWidth() {
                    return mBuilder.dividerHeight;
                }
            };
            ((ColorDrawable) mDivider).setColor(builder.dividerColor);
        } else {
            mDivider = builder.dividerDrawable;
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        c.save();
        int left, right, top, bottom;
        int count = parent.getChildCount();
        if (!mBuilder.isShowLastDivider) {
            count -= 1;
        }

        Context context = parent.getContext();
        for (int i = 0; i < count; i++) {
            final View child = parent.getChildAt(i);
            int transitionX = (int) ViewCompat.getTranslationX(child);
            int transitionY = (int) ViewCompat.getTranslationY(child);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (mBuilder.orientation == LinearLayoutManager.VERTICAL) {
                left = child.getLeft() - params.leftMargin + transitionX + ConvertUtils.dp2px(mBuilder.leftMargin);
                top = child.getBottom() + params.bottomMargin;
                right = child.getRight() + params.rightMargin - ConvertUtils.dp2px(mBuilder.rightMargin);
                bottom = top + mBuilder.dividerHeight + transitionY;
            } else {
                top = child.getTop() - params.topMargin + ConvertUtils.dp2px(mBuilder.topMargin);
                bottom = child.getBottom() + params.bottomMargin + transitionY - ConvertUtils.dp2px(mBuilder.bottomMargin);
                left = child.getRight() + params.rightMargin + transitionX;
                right = left + mBuilder.dividerHeight;
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if (!mBuilder.isShowLastDivider && position == itemCount - 1) {
            return;
        }
        if (mBuilder.orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mBuilder.dividerHeight);
        } else {
            outRect.set(0, 0, mBuilder.dividerHeight, 0);
        }
    }


    public static class Builder {

        private Drawable dividerDrawable;
        private int dividerHeight = DEFAULT_HEIGHT;
        private int dividerColor = Color.GRAY;
        private int orientation = LinearLayoutManager.VERTICAL;
        private int leftMargin, rightMargin, topMargin, bottomMargin;
        private boolean isShowLastDivider = true;

        public Builder setDividerDrawable(Drawable dividerDrawable) {
            this.dividerDrawable = dividerDrawable;
            return this;
        }

        public Builder setDividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public Builder setDividerColor(@ColorInt int color) {
            this.dividerColor = color;
            return this;
        }

        public Builder setOrientation(@RecyclerView.Orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setLeftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

        public Builder setTopMargin(int topMargin) {
            this.topMargin = topMargin;
            return this;
        }

        public Builder setBottomMargin(int bottomMargin) {
            this.bottomMargin = bottomMargin;
            return this;
        }

        public Builder isShowLastDivider(boolean flag) {
            this.isShowLastDivider = flag;
            return this;
        }

        public LinearLayoutDividerItemDecoration build() {
            return new LinearLayoutDividerItemDecoration(this);
        }
    }

}