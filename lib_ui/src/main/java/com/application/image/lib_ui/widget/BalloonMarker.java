package com.application.image.lib_ui.widget;/*
 * Copyright (C) 2015 Qiujuer <qiujuer@live.cn>
 * WebSite http://www.qiujuer.net
 * Created 08/04/2015
 * Changed 12/15/2015
 * Version 3.0.0
 * Author Qiujuer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * This is a BalloonMarker used to {@link SeekBar}
 * <p>
 * <p><strong>XML attributes</strong></p>
 * <p>
 * See {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gFont Attributes},
 * {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gMarkerBackgroundColor Attributes},
 * {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gMarkerElevation Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gMarkerSeparation Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gMarkerTextAppearance Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#BalloonMarker_gMarkerTextPadding Attributes}
 */
public class BalloonMarker extends ViewGroup implements BalloonMarkerDrawable.MarkerAnimationListener {
    private static final int ELEVATION_DP = 8;
    private static final int SEPARATION_DP = 0;
    //The drawable need new before this init method
    BalloonMarkerDrawable mBalloonMarkerDrawable = new BalloonMarkerDrawable(ColorStateList.valueOf(Color.TRANSPARENT), 0);
    //The TextView to show the info
    private TextView mNumber;
    //The max width of this View
    private int mWidth;
    //some distance between the thumb and our bubble marker.
    //This will be added to our measured height
    private int mSeparation;

    public BalloonMarker(Context context) {
        this(context, null);
    }

    public BalloonMarker(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.gBalloonMarkerStyle);
    }

    public BalloonMarker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, R.style.Genius_Widget_BalloonMarker, "0");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BalloonMarker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes, "0");
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(BalloonMarker.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(BalloonMarker.class.getName());
    }


    public void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, String maxValue) {
        final Resources resource = getResources();
        final float density = resource.getDisplayMetrics().density;

        mNumber = new TextView(context);
        mNumber.setGravity(Gravity.CENTER);
        mNumber.setText(maxValue);
        mNumber.setMaxLines(1);
        mNumber.setSingleLine(true);
        UiCompat.setTextDirection(mNumber, 5); //5 is TEXT_DIRECTION_LOCALE
        mNumber.setVisibility(View.INVISIBLE);

        // reset text size
        resetSizes(maxValue);

        mSeparation = (int) (SEPARATION_DP * density);

        mBalloonMarkerDrawable = new BalloonMarkerDrawable(ColorStateList.valueOf(Color.TRANSPARENT), 0);
        mBalloonMarkerDrawable.setCallback(this);
        mBalloonMarkerDrawable.setMarkerListener(this);
        mBalloonMarkerDrawable.setExternalOffset(getPaddingBottom());

        UiCompat.setOutlineProvider(this, mBalloonMarkerDrawable);

        if (attrs != null) {

            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BalloonMarker,
                    defStyleAttr, defStyleRes);
            int textPadding = a.getDimensionPixelSize(R.styleable.BalloonMarker_gMarkerTextPadding,
                    resource.getDimensionPixelSize(R.dimen.g_balloonMarker_textPadding));
            int textAppearanceId = a.getResourceId(R.styleable.BalloonMarker_gMarkerTextAppearance,
                    R.style.Genius_Widget_BalloonMarker_TextAppearance);
            ColorStateList color = a.getColorStateList(R.styleable.BalloonMarker_gMarkerBackgroundColor);
            String fontFile = a.getString(R.styleable.BalloonMarker_gFont);

            mSeparation = a.getDimensionPixelSize(R.styleable.BalloonMarker_gMarkerSeparation,
                    resource.getDimensionPixelSize(R.dimen.g_balloonMarker_separation));

            a.recycle();

            setTextPadding(textPadding);
            setTextAppearance(textAppearanceId);
            setBackgroundColor(color);

            //Elevation for android 5+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                float elevation = a.getDimension(R.styleable.BalloonMarker_gMarkerElevation, ELEVATION_DP * density);
                this.setElevation(elevation);
            }

            // Check for IDE preview render
            if (!this.isInEditMode()) {
                // Font
                if (fontFile != null && fontFile.length() > 0) {
                    Typeface typeface = Ui.getFont(getContext(), fontFile);
                    if (typeface != null) setTypeface(typeface);
                }
            }
        }
    }

    public void setSeparation(int separation) {
        this.mSeparation = separation;
    }

    public void setTextPadding(int padding) {
        mNumber.setPadding(padding, 0, padding, 0);
    }

    public void setTypeface(Typeface typeface) {
        mNumber.setTypeface(typeface);
    }

    public void setTextAppearance(int resId) {
        mNumber.setTextAppearance(getContext(), resId);
    }

    public ColorStateList getBackgroundColor() {
        return mBalloonMarkerDrawable.getColorStateList();
    }

    public void setBackgroundColor(ColorStateList color) {
        mBalloonMarkerDrawable.setColorStateList(color);
    }

    public void setClosedSize(float closedSize) {
        mBalloonMarkerDrawable.setClosedStateSize(closedSize);
    }

    public void resetSizes(String maxValue) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //Account for negative numbers... is there any proper way of getting the biggest string between our range????
        mNumber.setText(String.format("-%s", maxValue));
        //Do a first forced measure call for the TextView (with the biggest text content),
        //to calculate the max width and use always the same.
        //this avoids the TextView from shrinking and growing when the text content changes
        int wSpec = MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, MeasureSpec.AT_MOST);
        int hSpec = MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, MeasureSpec.AT_MOST);
        mNumber.measure(wSpec, hSpec);
        mWidth = Math.max(mNumber.getMeasuredWidth(), mNumber.getMeasuredHeight());
        removeView(mNumber);
        addView(mNumber, new FrameLayout.LayoutParams(mWidth, mWidth, Gravity.LEFT | Gravity.TOP));
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        if (mBalloonMarkerDrawable != null) {
            mBalloonMarkerDrawable.setExternalOffset(bottom);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mBalloonMarkerDrawable.draw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSize = mWidth + getPaddingLeft() + getPaddingRight();
        int heightSize = mWidth + getPaddingTop() + getPaddingBottom();
        //This diff is the basic calculation of the difference between
        //a square side size and its diagonal
        //this helps us account for the visual offset created by MarkerDrawable
        //when leaving one of the corners un-rounded
        int diff = (int) ((1.41f * mWidth) - mWidth) / 2;
        setMeasuredDimension(widthSize, heightSize + diff + mSeparation);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();
        //the TetView is always layout at the top
        mNumber.layout(left, top, left + mWidth, top + mWidth);
        //the MarkerDrawable uses the whole view, it will adapt itself...
        // or it seems so...
        mBalloonMarkerDrawable.setBounds(left, top, right, bottom);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mBalloonMarkerDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //HACK: Sometimes, the animateOpen() call is made before the View is attached
        //so the drawable cannot schedule itself to run the animation
        //I think we can call it here safely.
        //I've seen it happen in android 2.3.7
        animateOpen();
    }

    public CharSequence getValue() {
        return mNumber.getText();
    }

    public void setValue(CharSequence value) {
        mNumber.setText(value);
    }

    public void animateOpen() {
        mBalloonMarkerDrawable.stop();
        mBalloonMarkerDrawable.animateToPressed();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void animateClose() {
        mBalloonMarkerDrawable.stop();

        ViewPropertyAnimator animator = mNumber.animate();
        animator.alpha(0f);
        animator.setDuration(100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            animator.withEndAction(new Runnable() {
                @Override
                public void run() {
                    //We use INVISIBLE instead of GONE to avoid a requestLayout
                    mNumber.setVisibility(View.INVISIBLE);
                    mBalloonMarkerDrawable.animateToNormal();
                }
            });
        } else {
            animator.setListener(new AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //We use INVISIBLE instead of GONE to avoid a requestLayout
                    mNumber.setVisibility(View.INVISIBLE);
                    mBalloonMarkerDrawable.animateToNormal();
                }
            });
        }
        animator.start();
    }

    @Override
    public void onOpeningComplete() {
        mNumber.setVisibility(View.VISIBLE);

        ViewPropertyAnimator animator = mNumber.animate();
        animator.alpha(1f);
        animator.setDuration(100);
        animator.start();

        /*
        ViewCompat.animate(mNumber)
                .alpha(1f)
                .setDuration(100)
                .start();
        */

        if (getParent() instanceof BalloonMarkerDrawable.MarkerAnimationListener) {
            ((BalloonMarkerDrawable.MarkerAnimationListener) getParent()).onOpeningComplete();
        }
    }

    @Override
    public void onClosingComplete() {
        if (getParent() instanceof BalloonMarkerDrawable.MarkerAnimationListener) {
            ((BalloonMarkerDrawable.MarkerAnimationListener) getParent()).onClosingComplete();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBalloonMarkerDrawable.stop();
    }

    public void setColors(int startColor, int endColor) {
        mBalloonMarkerDrawable.setColors(startColor, endColor);
    }
}
