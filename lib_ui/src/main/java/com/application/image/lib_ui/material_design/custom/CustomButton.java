package com.application.image.lib_ui.material_design.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by yuhongwen
 * on 2020/10/21
 */
public class CustomButton extends View {
    public CustomButton(Context context) {
        super(context);
        initView(context);
    }

    public CustomButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

    }

    public CustomButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
