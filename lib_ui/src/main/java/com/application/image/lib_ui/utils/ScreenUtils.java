package com.application.image.lib_ui.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by yuhongwen
 * on 2020/10/29
 */
public class ScreenUtils {

    private static final int DEFAULT_HEIGHT = 0;

    /**
     * 获取屏幕的高
     *
     * @param context 当前上下文
     * @return 屏幕高
     */
    public static int getScreenHeightPix(Context context) {
        if (context == null) {
            return DEFAULT_HEIGHT;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 得到手机屏幕的宽度, 像素 pix 单位
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context){
        int resid = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resid > 0){
            return context.getResources().getDimensionPixelSize(resid);
        }
        return -1;
    }
}
