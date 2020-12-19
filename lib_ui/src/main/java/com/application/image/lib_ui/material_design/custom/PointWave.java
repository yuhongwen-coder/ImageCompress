package com.application.image.lib_ui.material_design.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.application.image.lib_ui.R;

import java.lang.ref.WeakReference;

/**
 * Created by yuhongwen
 * on 2020/12/17
 * 省略号动画
 */
public class PointWave extends LinearLayout {
    private static final int NUM = 5;
    private Context context;
    private ImageView mOldIM;
    private UpdateHandler handler;

    public PointWave(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PointWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PointWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        //初始化数据
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        handler = new UpdateHandler(context);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.point_waitingbar_red);
        LayoutParams tLayoutParams = new LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        tLayoutParams.leftMargin = 5;
        tLayoutParams.rightMargin = 5;

        //添加5个小点省略号
        for (int i = 0; i < NUM; i++) {
            ImageView vDot = new ImageView(context);
            vDot.setLayoutParams(tLayoutParams);
            if (i == 0) {
                vDot.setBackgroundResource(R.mipmap.point_waitingbar_white);
            } else {
                vDot.setBackgroundResource(R.mipmap.point_waitingbar_red);
            }
            this.addView(vDot);
        }
        mOldIM = (ImageView) this.getChildAt(0);
        handler.sendEmptyMessage(0);
    }

    //提供给外部消除message
    public void setDestroyCallBack() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private class UpdateHandler extends Handler {
        WeakReference<Context> reference;

        public UpdateHandler(Context context) {
            reference = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int cPosition = msg.what;
            if (mOldIM != null)
                mOldIM.setBackgroundResource(R.mipmap.point_waitingbar_red);
            ImageView currentIM = (ImageView) PointWave.this.getChildAt(cPosition);
            currentIM.setBackgroundResource(R.mipmap.point_waitingbar_white);
            mOldIM = currentIM;
            if (++cPosition == NUM)
                cPosition = 0;
            this.sendEmptyMessageDelayed(cPosition, 200);
        }
    }
}
