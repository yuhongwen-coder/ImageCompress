package com.application.image.lib_ui.material_design.recylerview.multi_adapter.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.utils.DensityUtil;


/**
 * Created by yuhongwen
 * on 2020/10/31
 * 绘制语音播放的圆弧
 */
public class DrawArcView extends View {

    private final int arcColor;
    private Paint paint;

    public DrawArcView(Context context) {
        this(context,null);
    }

    public DrawArcView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置的属性
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.arc_view);
        arcColor = tya.getColor(R.styleable.arc_view_arc_color,context.getResources().getColor(R.color.blue));
        init(context);
    }

    private void init(Context context) {
        // 设置画笔样式
        paint = new Paint();
        paint.setColor(arcColor);
        paint.setStrokeWidth(DensityUtil.dip2px(context, 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        RectF rect = new RectF(100,100,400,300);
        path.addArc(rect, 0,90);
        canvas.drawPath(path, paint);
    }
}
