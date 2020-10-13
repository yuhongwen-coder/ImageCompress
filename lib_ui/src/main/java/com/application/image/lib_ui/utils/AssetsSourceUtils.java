package com.application.image.lib_ui.utils;

import android.app.Application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yuhongwen
 * on 2020/10/12
 */
public class AssetsSourceUtils {
    private static Application context;
    private static final String TAG = "LoadAssetsResUtils";

    private static Application getApplication() {
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

    public static String getJsonString(String assetsRes) {
        context = getApplication();
        try {
            InputStream open = context.getAssets().open(assetsRes);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            bufferedReader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
