package com.application.image.lib_ui.material_design.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
 * https://blog.csdn.net/rhljiayou/article/details/7212620
 */
public class DrawArcView extends View {

    private final int arcColor;
    private Paint paint;
    private Point mStartPoint;
    private Point mControlPoint;
    private Point mEndPoint;
    private Path mBezierPath;
    private Path mPointPath;

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
        paint.setStrokeWidth(5);// 画笔的粗细
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setAntiAlias(true);//取消锯齿


        mStartPoint = new Point();
        mStartPoint.set(100, 300);
        mControlPoint = new Point();
        mControlPoint.set(300, 100);
        mEndPoint = new Point();
        mEndPoint.set(500, 500);


    }

    /**
     * 1 ：xml 设置的 宽高影响了 这里的绘制
     * 2 ：getWidth（） 是测量这个控件的 宽， onDraw() 只是画具体的内容，在画之前，控件已经通过 onMeasure测量出来了
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("yhw","DrawArcView onDraw");
        Path path = new Path();
        RectF rect = new RectF(50,50,400,300);
        path.addArc(rect, 0,90);
        canvas.drawPath(path, paint);


        RectF oval1=new RectF(50,40,90,90);
        canvas.drawArc(oval1, 270, 180, false, paint);//小弧形


        RectF oval2=new RectF(50,20,120,120);
        canvas.drawArc(oval2, 270, 180, false, paint);//小弧形

        RectF oval3=new RectF(50,10,180,180);
        canvas.drawArc(oval3, 270, 180, false, paint);//小弧形


//        mBezierPath = new Path();
//        mPointPath = new Path();
//
//        //贝塞尔
//        mBezierPath.moveTo(mStartPoint.x, mStartPoint.y);
//        mBezierPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);
//
//        //连接线
////        mPointPath.moveTo(mStartPoint.x, mStartPoint.y);
////        mPointPath.lineTo(mControlPoint.x, mControlPoint.y);
////        mPointPath.lineTo(mEndPoint.x, mEndPoint.y);
//
//        //绘制起始点、控制点、终点的连线
////        canvas.drawPath(mPointPath, paint);
//
//        //绘制贝塞尔
//        canvas.drawPath(mBezierPath, paint);








//        RectF rect1 = new RectF(100, 200, 400, 400);
//
//        path.addRect(rect1, Path.Direction.CCW);
//        canvas.drawRect(rect1, paint);

        //在(400, 200, 600, 400)区域内绘制一个300度的圆弧
//        RectF rectF = new RectF(400, 200, 600, 400);
//        path.addArc(rectF, 0, 300);
////在(400, 600, 600, 800)区域内绘制一个90度的圆弧，并且不连接两个点
//        RectF rectFTo = new RectF(400, 600, 600, 800);
//        path.arcTo(rectFTo, 0, 90, true);
////等价于path.addArc(rectFTo, 0, 90);
//        canvas.drawPath(path, paint);




//        /**
//         * 这是一个居中的圆
//////         */


//        float x = (getWidth() - getHeight() / 2) / 2;
//        float y = getHeight() / 4;
//
//        RectF oval = new RectF( x, y,
//                getWidth() - x, getHeight() - y);
//
//        canvas.drawOval(oval,paint);


        /*四个参数：
                参数一：矩形距离父view左边距离
                参数二：矩形距离父view上边距离
                参数三：矩形距离父view左边距离
                参数四：矩形距离父view上边距离
                */
        //定义一个矩形区域
//        RectF oval1 = new RectF(230, 10, 380, 110);
        //矩形区域内切椭圆
//        canvas.drawOval(oval1, paint);// drawOval 绘制椭圆

    }
}
