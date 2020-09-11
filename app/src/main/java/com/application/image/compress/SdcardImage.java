package com.application.image.compress;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/9/10
 */
public class SdcardImage {
    private List<String> imagePath;

    public static List<String> getSdcardImagePath(String path) {
         // /storage/emulated/0
        List<String> list = getPictures(path);
        return list;
    }

    private static List<String> getPictures(final String strPath) {
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

    // 现有的算法只能压缩图片
    private static String getPicture(String strPath) {
        File file = new File(strPath);
        if (file.isFile()) {
            int idx = file.getPath().lastIndexOf(".");
            if (idx <= 0) {
                return "";
            }
            String suffix = file.getPath().substring(idx);
            if (suffix.toLowerCase().equals(".jpg") ||
                    suffix.toLowerCase().equals(".jpeg") ||
                    suffix.toLowerCase().equals(".bmp") ||
                    suffix.toLowerCase().equals(".png") ||
                    suffix.toLowerCase().equals(".gif")) {
                return file.getPath();
            }
        }
        return "";
    }
}
