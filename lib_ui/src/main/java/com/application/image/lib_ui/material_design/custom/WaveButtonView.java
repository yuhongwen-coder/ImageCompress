package com.application.image.lib_ui.material_design.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.application.image.lib_ui.R;

/**
 * Created by yuhongwen
 * on 2020/10/21
 * 录音水波纹动画
 * https://www.jianshu.com/p/fe340e7963de
 */
public class WaveButtonView extends View {

    private int centerColor;
    private float centerRadius;
    private float maxRadius;
    private float waveWidth;
    private int waveIntervalTime;
    private int waveDuration;
    private Paint paint = new Paint();
    private boolean running;

    public WaveButtonView(Context context) {
        super(context);
    }

    public WaveButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }

    private void initView(Context context,AttributeSet attributeSet,int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WaveView, defStyleAttr, 0);
        centerColor = typedArray.getColor(R.styleable.WaveView_center_color, ContextCompat.getColor(context, R.color.color_897EFF));
        centerRadius = typedArray.getDimension(R.styleable.WaveView_center_radius, 4f);
        maxRadius = typedArray.getDimension(R.styleable.WaveView_max_radius, 14f);
        waveWidth = typedArray.getDimension(R.styleable.WaveView_wave_width, 1.0f);
        waveIntervalTime = typedArray.getInt(R.styleable.WaveView_wave_interval_time, 500);
        waveDuration = typedArray.getInt(R.styleable.WaveView_wave_duration, 1500);
        paint.setColor(centerColor);
        typedArray.recycle();
    }

    private void  setWaveStart(boolean waveStart) {
//        if (waveStart) {
//            if (!running) {
//                running = true;
//                waveList.add(Wave())
//            }
//        } else {
//            running = false
//            waveList.forEach {
//                it.cancelAnimation()
//            }
//        }
    }
}
