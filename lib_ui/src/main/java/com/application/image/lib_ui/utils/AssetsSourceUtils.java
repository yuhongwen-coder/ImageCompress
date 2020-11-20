package com.application.image.lib_ui.utils;

import android.app.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/10/12
 */
public class AssetsSourceUtils {
    private static Application context;
    private static final String TAG = "LoadAssetsResUtils";

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

    /**
     *  测试函数
     * @param strPath
     * @return
     */
    public static List<String> getPicturesTest(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for(int i = 0; i < allfiles.length; i++) {
            final File fi = allfiles[i];
            if(fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif") ) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }
}
