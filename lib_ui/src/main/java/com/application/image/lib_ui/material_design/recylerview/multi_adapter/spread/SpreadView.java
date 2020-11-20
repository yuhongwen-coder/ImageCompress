package com.application.image.lib_ui.material_design.recylerview.multi_adapter.spread;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.application.image.lib_ui.R;


/**
 * name: wy
 * date: 2020/9/1
 * desc:
 */
public class SpreadView extends LinearLayout {

    private Context mContext;

    private TextView tvMic;
    private WaveFrameLayout fl_wave_view;

    public SpreadView(Context context) {
        this(context,null);
    }
    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.layout_spread_view,this);
        tvMic = view.findViewById(R.id.tv_mic);
        fl_wave_view = view.findViewById(R.id.fl_wave_view);
        setTextMic("请点击后说话");
    }

    public void setTextMic(final String mic){
        post(new Runnable() {
            @Override
            public void run() {
                tvMic.setText(mic);
            }
        });
    }

    /**
     * 开启正在识别动画
     */
    public void startAnimator(){
        post(new Runnable() {
            @Override
            public void run() {
                fl_wave_view.startAnimator();
            }
        });

    }

    /**
     * 停止识别动画
     */
    public void stopAnimator(){
        post(new Runnable() {
            @Override
            public void run() {
                fl_wave_view.stopAnimator();
            }
        });
    }

    /**
     * 设置显示状态字体颜色
     */
    public void setMicColor(@ColorRes final int colorId){
        post(new Runnable() {
            @Override
            public void run() {
                tvMic.setTextColor(ContextCompat.getColor(mContext, colorId));
            }
        });
    }

}