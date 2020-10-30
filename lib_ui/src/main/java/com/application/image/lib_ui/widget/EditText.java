package com.application.image.lib_ui.widget;/*
 * Copyright (C) 2014-2016 Qiujuer <qiujuer@live.cn>
 * WebSite http://www.qiujuer.net
 * Author qiujuer
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


import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Property;
import android.view.Gravity;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


/**
 * EditText
 * This have a title from hint
 * <p>
 * <p><strong>XML attributes</strong></p>
 * <p>
 * See {@link net.qiujuer.genius.ui.R.styleable#EditText_gFont Attributes},
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitle Attributes},
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitlePaddingBottom Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitlePaddingLeft Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitlePaddingRight Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitlePaddingTop Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gHintTitleTextSize Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gLineColor Attributes}
 * {@link net.qiujuer.genius.ui.R.styleable#EditText_gLineSize Attributes}
 */
@SuppressWarnings("unused")
public class EditText extends androidx.appcompat.widget.AppCompatEditText {
    private TextPaint mTitlePaint;
    private TextWatcher mTextWatcher;
    private TitleProperty mCurTitleProperty;

    private ObjectAnimator mAnimator;
    private boolean isHaveText;
    private boolean isAttachWindow;

    private int mLineSize;
    private ColorStateList mLineColor;

    private int mHintTitleModel;
    private int mHintTitleTextSize;
    private Rect mHintTitlePadding = new Rect();

    private int mTruePaddingTop;

    public EditText(Context context) {
        super(context);
    }

    public EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, R.attr.gEditTextStyle, R.style.Genius_Widget_EditText);
    }

    public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, R.style.Genius_Widget_EditText);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null)
            return;

        // Get the super padding top
        mTruePaddingTop = super.getPaddingTop();

        final Context context = getContext();
        final Resources resources = getResources();

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.EditText, defStyleAttr, defStyleRes);

        String fontFile = a.getString(R.styleable.EditText_gFont);
        int lineSize = a.getDimensionPixelSize(R.styleable.EditText_gLineSize, resources.getDimensionPixelSize(R.dimen.g_editText_lineSize));
        ColorStateList lineColor = a.getColorStateList(R.styleable.EditText_gLineColor);

        // Set HintProperty
        int titleModel = a.getInt(R.styleable.EditText_gHintTitle, 1);
        int titleTextSize = a.getDimensionPixelSize(R.styleable.EditText_gHintTitleTextSize, resources.getDimensionPixelSize(R.dimen.g_editText_hintTitleTextSize));
        int titlePaddingTop = a.getDimensionPixelSize(R.styleable.EditText_gHintTitlePaddingTop, 0);
        int titlePaddingBottom = a.getDimensionPixelSize(R.styleable.EditText_gHintTitlePaddingBottom, 0);
        int titlePaddingLeft = a.getDimensionPixelSize(R.styleable.EditText_gHintTitlePaddingLeft, 0);
        int titlePaddingRight = a.getDimensionPixelSize(R.styleable.EditText_gHintTitlePaddingRight, 0);

        a.recycle();

        // Init color
        if (lineColor == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                lineColor = resources.getColorStateList(R.color.g_default_edit_view_line, null);
            } else {
                //noinspection deprecation
                lineColor = resources.getColorStateList(R.color.g_default_edit_view_line);
            }
        }

        if (!Ui.isHaveAttribute(attrs, "textColorHint") || getHintTextColors() == null) {
            ColorStateList hintColor;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hintColor = resources.getColorStateList(R.color.g_default_edit_view_hint, null);
            } else {
                //noinspection deprecation
                hintColor = resources.getColorStateList(R.color.g_default_edit_view_hint);
            }
            setHintTextColor(hintColor);
        }

        // Set same values
        setLineSize(lineSize);
        setLineColor(lineColor);

        setHintTitleTextSize(titleTextSize);
        setHintTitleModel(titleModel);

        // check for IDE preview render
        if (!this.isInEditMode()) {
            // Set Font
            if (fontFile != null && fontFile.length() > 0) {
                Typeface typeface = Ui.getFont(context, fontFile);
                if (typeface != null) setTypeface(typeface);
            }
        }

        // Init background and title
        if (!Ui.isHaveAttribute(attrs, "background")) {
            initBackground();
        }

        initHintTitleText();

        // SetHintPadding
        setHintTitlePadding(titlePaddingLeft, titlePaddingTop, titlePaddingRight, titlePaddingBottom);
    }


    private void initBackground() {
        final int lineSize = getLineSize();
        Drawable background;
        if (lineSize == 0) {
            background = null;
        } else {
            int increment = getResources().getDimensionPixelSize(R.dimen.g_editText_lineSize_active_increment);
            int lineActive = lineSize + increment;
            int lineHalf = lineSize >> 1;

            // Creating normal state drawable
            ShapeDrawable normal = new ShapeDrawable(new BorderShape(new RectF(0, 0, 0, lineSize)));
            normal.getPaint().setColor(getLineColor(new int[]{android.R.attr.state_enabled}));

            // Creating pressed state drawable
            ShapeDrawable pressed = new ShapeDrawable(new BorderShape(new RectF(0, 0, 0, lineActive)));
            pressed.getPaint().setColor(getLineColor(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}));

            // Creating focused state drawable
            ShapeDrawable focused = new ShapeDrawable(new BorderShape(new RectF(0, 0, 0, lineActive)));
            focused.getPaint().setColor(getLineColor(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}));

            // Creating disabled state drawable
            ShapeDrawable disabled = new ShapeDrawable(new BorderShape(new RectF(0, 0, 0, lineHalf), lineHalf, lineSize));
            disabled.getPaint().setColor(getLineColor(new int[]{-android.R.attr.state_enabled}));
            // disabled.getPaint().setAlpha(0xA0);

            Drawable[] drawable = new Drawable[]{pressed, focused, normal, disabled};
            background = createStateListDrawable(drawable);

        }

        // Set Background
        UiCompat.setBackground(this, background);

    }

    private static StateListDrawable createStateListDrawable(Drawable drawable[]) {
        if (drawable == null || drawable.length < 4)
            return null;
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, drawable[0]);
        states.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}, drawable[1]);
        states.addState(new int[]{android.R.attr.state_enabled}, drawable[2]);
        states.addState(new int[]{-android.R.attr.state_enabled}, drawable[3]);
        return states;
    }

    private void initHintTitleText() {
        if (isShowTitle()) {
            // Set up a default TextPaint object
            if (mTitlePaint == null) {
                mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                mTitlePaint.density = getResources().getDisplayMetrics().density;
                mTitlePaint.setTextAlign(Paint.Align.LEFT);
                mTitlePaint.setTypeface(getTypeface());
            }

            // Add Watcher
            if (mTextWatcher == null) {
                mTextWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        checkShowTitle(s, false);
                    }
                };
                addTextChangedListener(mTextWatcher);
            }

            // try show
            Editable editable = getEditableText();
            checkShowTitle(editable, false);
        } else {
            if (mTextWatcher != null) {
                removeTextChangedListener(mTextWatcher);
                mTextWatcher = null;
            }

            mTitlePaint = null;
            mCurTitleProperty = null;
            mAnimator = null;
        }
    }

    /**
     * Check show hint title
     *
     * @param s          The text, if the have same string we should move hint
     * @param skipChange on showed we not refresh ui, but skipChange=true,
     *                   we can skip the check
     */
    private void checkShowTitle(Editable s, boolean skipChange) {
        // in this we can check width
        if (isShowTitle() && getWidth() > 0) {
            boolean have = s != null && s.length() > 0;
            if (have != isHaveText || (have && skipChange)) {
                isHaveText = have;
                animateShowTitle(isHaveText);
            }
        }
    }

    private boolean isShowTitle() {
        return mHintTitleModel != 0;
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
        if (mTitlePaint != null)
            mTitlePaint.setTypeface(tf);
    }

    @Override
    public int getPaddingTop() {
        return mTruePaddingTop;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mTruePaddingTop = top;

        if (isShowTitle()) {
            top += mHintTitleTextSize + mHintTitlePadding.top + mHintTitlePadding.bottom;
        }

        super.setPadding(left, top, right, bottom);
    }

    public Rect getHintTitlePadding() {
        return mHintTitlePadding;
    }

    public void setHintTitlePadding(int left, int top, int right, int bottom) {
        mHintTitlePadding.set(left, top, right, bottom);
        // We should reset padding
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }

    public void setLineSize(int lineSize) {
        if (mLineSize != lineSize) {
            this.mLineSize = lineSize;
            invalidate();
        }
    }

    public void setLineColor(ColorStateList lineColor) {
        if (mLineColor != lineColor) {
            this.mLineColor = lineColor;
            invalidate();
        }
    }

    public void setHintTitleModel(int model) {
        if (mHintTitleModel != model) {
            this.mHintTitleModel = model;
            initHintTitleText();
            // We should reset padding
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
            invalidate();
        }
    }

    public void setHintTitleTextSize(int titleTextSize) {
        if (mHintTitleTextSize != titleTextSize) {
            this.mHintTitleTextSize = titleTextSize;
            invalidate();
        }
    }

    public int getLineSize() {
        return mLineSize;
    }

    public ColorStateList getLineColor() {
        return mLineColor;
    }

    private int getLineColor(int[] status) {
        ColorStateList colors = getLineColor();
        if (colors == null)
            return 0;
        return colors.getColorForState(status, colors.getDefaultColor());
    }

    public int getHintTitleModel() {
        return mHintTitleModel;
    }

    public int getTitleTextSize() {
        return mHintTitleTextSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw Title Text
        if (isShowTitle() && mTitlePaint != null && mCurTitleProperty != null && mCurTitleProperty.mAlpha != 0) {
            CharSequence buf = getHint();
            if (buf != null) {
                int color = getCurrentHintTextColor();
                int alpha = Ui.modulateAlpha(Color.alpha(color), mCurTitleProperty.mAlpha);

                if (color != 0 && alpha != 0 && mCurTitleProperty.mTextSize > 0) {
                    mTitlePaint.setTextSize(mCurTitleProperty.mTextSize);
                    mTitlePaint.setColor(color);
                    mTitlePaint.setAlpha(alpha);

                    final int scrollX = getScrollX();
                    final int scrollY = getScrollY();
                    if ((scrollX | scrollY) == 0) {
                        canvas.drawText(buf, 0, buf.length(),
                                mCurTitleProperty.mLeft,
                                mCurTitleProperty.mTop + mCurTitleProperty.mTextSize,
                                mTitlePaint);
                    } else {
                        canvas.translate(scrollX, scrollY);
                        canvas.drawText(buf, 0, buf.length(),
                                mCurTitleProperty.mLeft,
                                mCurTitleProperty.mTop + mCurTitleProperty.mTextSize,
                                mTitlePaint);
                        canvas.translate(-scrollX, -scrollY);
                    }
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttachWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttachWindow = false;
        if (mTextWatcher != null) {
            removeTextChangedListener(mTextWatcher);
            mTextWatcher = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean isAttachWindow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return isAttachWindow;
        else
            return isAttachedToWindow();
    }

    @Override
    public void invalidate() {
        if (isAttachWindow())
            super.invalidate();
    }

    @Override
    public void setGravity(int gravity) {
        int old = getGravity();
        super.setGravity(gravity);
        // on change the gravity, we should try refresh hint
        if (old != gravity)
            checkShowTitle(getEditableText(), true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // on change the size, we should try refresh hint
        checkShowTitle(getEditableText(), true);
    }

    /**
     * =============================================================================================
     * The Animate
     * =============================================================================================
     */

    /**
     * Refresh hint ui by this
     *
     * @param value Hint TitleProperty
     */
    private void setTitleProperty(TitleProperty value) {
        mCurTitleProperty = value;
        invalidate();
    }

    private int getTextLen() {
        Paint paint = getPaint();
        if (mTitlePaint != null)
            return (int) paint.measureText(getHint().toString());
        else
            return 0;
    }

    private int getHintTextLen(int size) {
        Paint paint = mTitlePaint;
        if (paint != null) {
            paint.setTextSize(size);
            return (int) paint.measureText(getHint().toString());
        } else
            return 0;
    }

    private TitleProperty getStartProperty(boolean show) {
        TitleProperty property = new TitleProperty();
        if (mCurTitleProperty != null) {
            property.copy(mCurTitleProperty);
        } else {
            if (show) {
                copyTextProperty(property);
            } else {
                copyHintProperty(property);
            }
        }
        return property;
    }

    private TitleProperty getEndProperty(boolean show) {
        TitleProperty property = new TitleProperty();
        if (show) {
            copyHintProperty(property);
        } else {
            copyTextProperty(property);
        }
        return property;
    }

    @SuppressLint("RtlHardcoded")
    private TitleProperty copyTextProperty(TitleProperty property) {
        int gravity = getGravity();
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.START:
            case Gravity.LEFT:
                property.mLeft = getPaddingLeft();
                break;
            case Gravity.END:
            case Gravity.RIGHT:
                property.mLeft = getWidth() - getPaddingRight() - getTextLen();
                break;
            case Gravity.CENTER_HORIZONTAL:
                int lp = getPaddingLeft();
                int rp = getPaddingRight();
                int center = lp + ((getWidth() - lp - rp) >> 1);
                int halfTextLen = getTextLen() / 2;
                property.mLeft = center - halfTextLen;
                break;
            default:
                property.mLeft = getPaddingLeft();
                break;
        }

        property.mAlpha = 0;
        property.mTextSize = (int) getTextSize();
        property.mTop = super.getPaddingTop();

        return property;
    }

    @SuppressLint("RtlHardcoded")
    private TitleProperty copyHintProperty(TitleProperty property) {
        property.mTop = getPaddingTop() + mHintTitlePadding.top;
        property.mAlpha = 255;
        property.mTextSize = mHintTitleTextSize;

        int gravity = getGravity();
        switch (gravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.START:
            case Gravity.LEFT:
                property.mLeft = getPaddingLeft() + mHintTitlePadding.left;
                break;
            case Gravity.END:
            case Gravity.RIGHT:
                property.mLeft = getWidth() - getPaddingRight() - mHintTitlePadding.right - getHintTextLen(property.mTextSize);
                break;
            case Gravity.CENTER_HORIZONTAL:
                int lp = getPaddingLeft() + mHintTitlePadding.left;
                int rp = getPaddingRight() + mHintTitlePadding.right;
                int center = lp + ((getWidth() - lp - rp) >> 1);
                int halfTextLen = getHintTextLen(property.mTextSize) / 2;
                property.mLeft = center - halfTextLen;
                break;
            default:
                property.mLeft = getPaddingLeft() + mHintTitlePadding.left;
                break;
        }

        return property;
    }

    private void animateShowTitle(boolean show) {
        TitleProperty pStart = getStartProperty(show);
        TitleProperty pEnd = getEndProperty(show);

        ObjectAnimator animator = getTitleAnimator();
        animator.setObjectValues(pStart, pEnd);

        if (isAttachWindow()) {
            animator.start();
        } else {
            setTitleProperty(pEnd);
        }
    }

    private ObjectAnimator getTitleAnimator() {
        if (mAnimator == null) {
            if (mCurTitleProperty == null)
                mCurTitleProperty = new TitleProperty();
            mAnimator = ObjectAnimator.ofObject(this, TITLE_PROPERTY, new TitleEvaluator(mCurTitleProperty), mCurTitleProperty);
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.setInterpolator(ANIMATION_INTERPOLATOR);
        } else {
            mAnimator.cancel();
        }
        return mAnimator;
    }

    /**
     * =============================================================================================
     * The custom properties
     * =============================================================================================
     */

    final static class TitleProperty {
        private int mTextSize;
        private int mAlpha = 255;
        private int mLeft;
        private int mTop;

        public void copy(TitleProperty property) {
            this.mTextSize = property.mTextSize;
            this.mAlpha = property.mAlpha;
            this.mLeft = property.mLeft;
            this.mTop = property.mTop;
        }
    }

    private final static class TitleEvaluator implements TypeEvaluator<TitleProperty> {

        private final TitleProperty mProperty;

        TitleEvaluator(TitleProperty property) {
            mProperty = property;
        }

        @Override
        public TitleProperty evaluate(float fraction, TitleProperty startValue, TitleProperty endValue) {
            mProperty.mLeft = (int) (startValue.mLeft + (endValue.mLeft - startValue.mLeft) * fraction);
            mProperty.mTop = (int) (startValue.mTop + (endValue.mTop - startValue.mTop) * fraction);
            mProperty.mTextSize = (int) (startValue.mTextSize + (endValue.mTextSize - startValue.mTextSize) * fraction);
            mProperty.mAlpha = (int) (startValue.mAlpha + (endValue.mAlpha - startValue.mAlpha) * fraction);
            return mProperty;
        }
    }

    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    private static final int ANIMATION_DURATION = 250;

    private final static Property<EditText, TitleProperty> TITLE_PROPERTY = new Property<EditText, TitleProperty>(TitleProperty.class, "titleProperty") {
        @Override
        public TitleProperty get(EditText object) {
            return object.mCurTitleProperty;
        }

        @Override
        public void set(EditText object, TitleProperty value) {
            object.setTitleProperty(value);
        }
    };
}
