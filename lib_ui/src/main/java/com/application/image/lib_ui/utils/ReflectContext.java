package com.application.image.lib_ui.utils;

import android.app.Application;

/**
 * Created by yuhongwen
 * on 2020/10/23
 */
public class ReflectContext {

    public static Application getApplication() {
        try {
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app != null) {
                return (Application) app;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
