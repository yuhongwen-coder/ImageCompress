package com.application.image.lib_ui.material_design.recylerview.multi_adapter.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants;
import com.application.image.lib_ui.utils.DensityUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yuhongwen
 * on 2020/11/16
 * 绘制没有国籍的国家图标icon + 文字
 */
public class LanguageIconImageView extends androidx.appcompat.widget.AppCompatImageView {

    private Context mContext;
    private Paint mPaintCircle;
    private int mHeightIcon;
    private int mWidthIcon;
    private Paint mPaintText;
    private static Map<String,Integer> languageColor = new HashMap<>();
    private String languageContent;
    private int circle = 35;
    private int textSize = 30;

    public LanguageIconImageView(Context context) {
        this(context,null);
    }

    public LanguageIconImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LanguageIconImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = getContext();
        initLanguageColor();
        init();
    }

    /**
     *  languageColor key 语言 value 语言对应的颜色
     */
    private void initLanguageColor() {
        if (languageColor.size() <=0) {
            languageColor.put(LanguageConstants.LANGUAGE_EN,Color.RED);
            languageColor.put(LanguageConstants.LANGUAGE_AR,Color.BLUE);
        }
    }

    public void setCountryLanguage(String language,int circle,int textSize) {
        this.languageContent = language;
        this.circle = circle;
        this.textSize = textSize;
        configPaint();
    }

    private void configPaint() {
        if (mPaintCircle != null) {
            int resRolor = languageColor.get(languageContent);
            mPaintCircle.setColor(resRolor);
        }
        if (mPaintText != null) {
            mPaintText.setTextSize(DensityUtil.dip2px(mContext, textSize));
        }
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        Log.e("CountryIconCopy","构造函数 enter init");
        // 设置画笔样式(画圆弧)
        mPaintCircle = new Paint();
        mPaintCircle.setColor(Color.BLUE);
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setAntiAlias(false);

        // 设置画笔样式（画文字）
        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        mPaintText.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(DensityUtil.dip2px(mContext, textSize));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("CountryIconCopy","onDraw enter");
        drawInCircle(canvas);
        drawInText(canvas);
    }

    private void drawInText(Canvas canvas) {
        Log.e("CountryIconCopy","drawInText mWidthIcon = " + mWidthIcon + "---mHeightIcon =" + mHeightIcon);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        // 绘制居中文字
        float textWidth = mPaintText.measureText("阿");
        // 文字baseline在y轴方向的位置
        float baseLineY = Math.abs(mPaintText.ascent() + mPaintText.descent()) / 2;
        canvas.drawText("阿", -textWidth / 2, baseLineY, mPaintText);
    }

    private void drawInCircle(Canvas canvas) {
        Log.e("CountryIconCopy","drawInCircle mWidthIcon = " + mWidthIcon + "---mHeightIcon =" + mHeightIcon);
        canvas.drawCircle(mWidthIcon / 2, mHeightIcon / 2, DensityUtil.dip2px(mContext, circle), mPaintCircle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("CountryIconCopy","onMeasure enter");
        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        // 获取宽度
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            // match_parent
            mWidthIcon = myWidthSpecSize;
        } else {
            // wrap_content
            mWidthIcon = DensityUtil.dip2px(mContext, 120);
        }
        // 获取高度
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            mHeightIcon = myHeightSpecSize;
        } else {
            // wrap_content
            mHeightIcon = DensityUtil.dip2px(mContext, 120);
        }
        // 设置该view的宽高
        setMeasuredDimension((int) mWidthIcon, (int) mHeightIcon);
    }

    /**
     * 绘制这个国家的圆
     */
    public static class CountryCircle {
        public CountryCircle(int color) {
            this.color = color;
        }
        int color;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    /**
     * 绘制这个国家的文字
     */
    public static class CountryText {
        public CountryText(int textSize,String textCOntent) {
            this.textSize = textSize;
            this.textContent = textCOntent;
        }
        private String textContent;
        private int textSize;

        public String getTextContent() {
            return textContent;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }
    }
}
