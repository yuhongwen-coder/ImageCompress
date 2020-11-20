package com.application.image.lib_ui.material_design.recylerview.multi_adapter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 水波纹动画
 */
public class RippleView extends View {

    private Context mContext;

    // 画笔对象
    private Paint mPaint;

    // View宽
    private float mWidth;

    // View高
    private float mHeight;

    // 声波的圆圈集合
    private List<Circle> mRipples = new ArrayList<>();

    private int sqrtNumber;

    // 圆圈扩散的速度
    private int mSpeed;

    // 圆圈之间的密度
    private int mDensity;

    // 圆圈的颜色
    private int mColor;

    // 圆圈是否为填充模式
    private boolean mIsFill;

    // 圆圈是否为渐变模式
    private boolean mIsAlpha;
    private Handler mHandler;
    private Runnable refreshRunnable;
    private static final long SPREAD_SIZE_UPDATE_TIME = 0;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置属性
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.mRippleView);
        mColor = tya.getColor(R.styleable.mRippleView_cColor, Color.BLUE);
        mSpeed = tya.getInt(R.styleable.mRippleView_cSpeed, 1);
        mDensity = tya.getInt(R.styleable.mRippleView_cDensity, 10);
        mIsFill = tya.getBoolean(R.styleable.mRippleView_cIsFill, false);
        mIsAlpha = tya.getBoolean(R.styleable.mRippleView_cIsAlpha, false);
        tya.recycle();
        initMainHandler();
        init();
    }

    private void initMainHandler() {
        mHandler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
//                Log.e("yuhongwen", "startRipple run ");
                invalidate();
                mHandler.postDelayed(refreshRunnable, SPREAD_SIZE_UPDATE_TIME);
            }
        };
    }

    private void init() {
        mContext = getContext();

        // 设置画笔样式
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        if (mIsFill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mDensity = DensityUtil.dip2px(mContext, mDensity);

        // 设置View的圆为半透明
        setBackgroundColor(Color.TRANSPARENT);
    }

    private void initFirstCircle() {
        // 添加第一个圆圈
//        Log.e("yuhongwen", "initFirstCircle mRipples size = " + mRipples.size());
        if (mRipples.size() > 0) {
            mRipples.clear();
        }
        Circle c = new Circle(20, 255);
        mRipples.add(c);
    }

    @Override
    public void onDraw(Canvas canvas) {
//        Log.e("yuhongwen", "onDraw = " );
        super.onDraw(canvas);
        drawInCircle(canvas);
    }

    /**
     * 圆到宽度
     *
     * @param canvas
     */
    private void drawInCircle(Canvas canvas) {
//        Log.e("yuhongwen", "drawInCircle mRipples size = " + mRipples.size());
        canvas.save();
        // 处理每个圆的宽度和透明度
        for (int i = 0; i < mRipples.size(); i++) {
            Circle c = mRipples.get(i);
            mPaint.setAlpha(c.alpha);// （透明）0~255（不透明）
            canvas.drawCircle(mWidth / 2, mHeight / 2, c.width - mPaint.getStrokeWidth(), mPaint);

            // 当圆超出View的宽度后删除
            if (c.width > mWidth / 2) {
                mRipples.remove(i);
            } else {
                // 计算不透明的数值，这里有个小知识，就是如果不加上double的话，255除以一个任意比它大的数都将是0
                if (mIsAlpha) {
                    double alpha = 255 - c.width * (255 / ((double) mWidth / 2));
                    c.alpha = (int) alpha;
                }
                // 修改这个值控制速度
                c.width += mSpeed;
            }
        }
        // 里面添加圆
        if (mRipples.size() > 0) {
            // 控制第二个圆出来的间距
            if (mRipples.get(mRipples.size() - 1).width > DensityUtil.dip2px(mContext, mDensity)) {
                mRipples.add(new Circle(0, 255));
            }
//            mRipples.add(new Circle(0, 255));
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 获取宽度
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            // match_parent
            mWidth = myWidthSpecSize;
        } else {
            // wrap_content
            mWidth = DensityUtil.dip2px(mContext, 120);
        }

        // 获取高度
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = myHeightSpecSize;
        } else {
            // wrap_content
            mHeight = DensityUtil.dip2px(mContext, 120);
        }

        // 设置该view的宽高
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }

    public void startRipple() {
//        Log.e("yuhongwen", "startRipple mRipples = " +mRipples.size());
        if (mRipples.size() >0) {
            return;
        }
        initFirstCircle();
        setVisibility(VISIBLE);
//        Log.e("yuhongwen", "startRipple add circle to mRipples = ");
        mHandler.postDelayed(refreshRunnable, SPREAD_SIZE_UPDATE_TIME);
    }

    public void stopRipple() {
//        Log.e("yuhongwen", "stopRipple");
        mRipples.clear();
        mHandler.removeCallbacks(refreshRunnable);
        setVisibility(GONE);
        invalidate();
    }


    class Circle {
        Circle(int width, int alpha) {
            this.width = width;
            this.alpha = alpha;
        }
        int width;
        int alpha;
    }

}