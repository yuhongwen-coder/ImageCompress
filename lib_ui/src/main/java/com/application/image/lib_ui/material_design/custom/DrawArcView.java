package com.application.image.lib_ui.material_design.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
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
        Log.e("yhw","DrawArcView context");
    }

    public DrawArcView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
        Log.e("yhw","DrawArcView context attrs");
    }

    public DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置的属性
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.arc_view);
        arcColor = tya.getColor(R.styleable.arc_view_arc_color,context.getResources().getColor(R.color.colorAccent));
        tya.recycle();
        init(context);
        Log.e("yhw","DrawArcView context attrs defStyleAttr");
    }

    private void init(Context context) {
        // 设置画笔样式
        paint = new Paint();
        paint.setColor(arcColor);
        paint.setStrokeWidth(DensityUtil.dip2px(context, 20));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);//取消锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("yhw","DrawArcView onDraw");
        Path path = new Path();


//        RectF rect = new RectF(100,100,400,300);
//        path.addArc(rect, 0,90);
//        canvas.drawPath(path, paint);
//
//        RectF rect1 = new RectF(100, 200, 400, 400);
//
//        path.addRect(rect1, Path.Direction.CCW);
//        canvas.drawRect(rect1, paint);

        //在(400, 200, 600, 400)区域内绘制一个300度的圆弧
        RectF rectF = new RectF(400, 200, 600, 400);
        path.addArc(rectF, 0, 300);
//在(400, 600, 600, 800)区域内绘制一个90度的圆弧，并且不连接两个点
        RectF rectFTo = new RectF(400, 600, 600, 800);
        path.arcTo(rectFTo, 0, 90, true);
//等价于path.addArc(rectFTo, 0, 90);
        canvas.drawPath(path, paint);




//        /**
//         * 这是一个居中的圆
////         */
//        float x = (getWidth() - getHeight() / 2) / 2;
//        float y = getHeight() / 4;
//
//        RectF oval = new RectF( x, y,
//                getWidth() - x, getHeight() - y);
//
//        canvas.drawArc(oval,360,140,true,paint);




    }
}
