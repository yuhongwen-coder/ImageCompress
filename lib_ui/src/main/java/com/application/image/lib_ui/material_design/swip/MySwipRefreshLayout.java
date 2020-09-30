package com.application.image.lib_ui.material_design.swip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.application.image.lib_ui.R;

import java.util.zip.Inflater;

/**
 * Created by yuhongwen
 * on 2020/9/29
 */
public class MySwipRefreshLayout extends SwipeRefreshLayout {

    public MySwipRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public MySwipRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initCustomView(Context context) {
        View headerView = View.inflate(context,R.layout.refersh_header_layout,null);
    }

    private void initView(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySwipRefreshLayout);

        // 可移动区域背景
        @SuppressLint("ResourceAsColor")
        int color = typedArray.getColor(R.styleable.MySwipRefreshLayout_swip_colour,R.color.text_red);
    }

}
