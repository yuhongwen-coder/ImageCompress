package com.application.image.lib_ui.material_design.animation.valueanimation;

import android.animation.ValueAnimator;
import android.content.Context;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.application.image.lib_ui.R;

/**
 * Author: ml
 * Date: 2019/4/25 11:14
 * Description:
 */
public class SpreadView extends LinearLayout{

    // 类标识
    private String TAG = SpreadView.class.getSimpleName();

    ImageView ivWave;
    ImageView ivWave1;
    ImageView ivWave2;
    TextView tvMic;



    private int visible;

    private boolean isOffline;  //是否是离线语音

    public interface TranslateResultListener {
        void onTranslateResultListener(String r);
    }

    private TranslateResultListener translateResultListener;

    public void setTranslateResultListener(TranslateResultListener listener) {
        this.translateResultListener = listener;
    }

    public SpreadView(Context context) {
        this(context, null, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimator2;
    private ValueAnimator valueAnimator3;

    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_spread_view, this);
        ivWave = view.findViewById(R.id.iv_wave);
        ivWave1 = view.findViewById(R.id.iv_wave_1);
        ivWave2 = view.findViewById(R.id.iv_wave_2);
        tvMic = view.findViewById(R.id.tv_mic);



        valueAnimator3 = ValueAnimator.ofFloat(1, 1.25f, 1);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                ivWave.setScaleX(value);
                ivWave.setScaleY(value);
                ivWave.setAlpha((1.3f-value)*5);

            }
        });
        valueAnimator3.setDuration(800);
        valueAnimator3.setRepeatCount(Animation.INFINITE);
        valueAnimator3.setInterpolator(new DecelerateInterpolator());

        valueAnimator2 = ValueAnimator.ofFloat(1, 1.25f, 1);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                ivWave1.setScaleX(value);
                ivWave1.setScaleY(value);
                ivWave1.setAlpha((1.4f-value)*5);
                if (value > 1.08f) {
                    if (!valueAnimator3.isRunning()) {
                        valueAnimator3.start();

                    }
                }
            }
        });
        valueAnimator2.setInterpolator(new DecelerateInterpolator());
        valueAnimator2.setRepeatCount(Animation.INFINITE);
        valueAnimator2.setDuration(800);

        valueAnimator = ValueAnimator.ofFloat(1, 1.2f, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                ivWave2.setScaleX(value);
                ivWave2.setScaleY(value);
                ivWave2.setAlpha((1.5f-value)*5);

                if (value > 1.06f) {
                    if (!valueAnimator2.isRunning()) {
                        valueAnimator2.start();

                    }
                }


            }
        });
        valueAnimator.setRepeatCount(Animation.INFINITE);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(800);

    }



    /**
     * 设置字体颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        tvMic.setTextColor(color);
    }

    private boolean isWake;


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        visible = visibility;
        if (visibility != View.VISIBLE) {
            //不可见的时候停止识别
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //反注册
    }




    private void startSpreadAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                valueAnimator.start();
            }
        });
    }

    public void stopSpreadAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                valueAnimator.cancel();
                valueAnimator2.cancel();
                valueAnimator3.cancel();
                ivWave.setScaleX(1);
                ivWave.setScaleY(1);
                ivWave.setAlpha(1f);

                ivWave1.setScaleX(1);
                ivWave1.setScaleY(1);
                ivWave1.setAlpha(1f);

                ivWave2.setScaleX(1);
                ivWave2.setScaleY(1);
                ivWave2.setAlpha(1f);

                String text = "";
                tvMic.setText(text);
            }
        });

    }

}
