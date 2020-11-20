package com.application.image.lib_ui.material_design.recylerview.multi_adapter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by yuhongwen
 * on 2020/10/27
 *  语音动画 集合Viw
 *  1： 国籍图标 RoundedImageView
 *  2： 开启动画的水波纹 RippleView
 */
public class CustomSpreadView extends FrameLayout {
    public CustomSpreadView(@NonNull Context context) {
        super(context);
    }

    public CustomSpreadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpreadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
