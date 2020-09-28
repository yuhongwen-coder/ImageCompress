package com.application.image.compress;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

/**
 * Created by yuhongwen
 * on 2020/9/10
 */
public class ImageCompressUtils {
    private static final String TAG = "ImageCompressUtils";
    public static void compressImage(final Context context, String srcImage, final String destImage) {
        String reFileName = "";
        if (!TextUtils.isEmpty(srcImage)) {
            String[] imagePaths = srcImage.split(destImage);
            if (imagePaths != null && imagePaths.length>=2) {
                reFileName = imagePaths[1];
                if (reFileName.startsWith("/")) {
                    // 删掉 "/"
                    reFileName = reFileName.substring(1);
                }
            }
        }
        final String finalReFileName = reFileName;
        Luban.with(context)
                .load(srcImage)
                .ignoreBy(100)
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        Log.e(TAG,"setRenameListener。。。。");
                        return finalReFileName;
                    }
                })
                .setTargetDir(destImage)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply (String path){
                        Log.e(TAG,"apply。。。。");
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart () {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                Log.e(TAG,"开始压缩。。。。");
            }

            @Override
            public void onSuccess (File file){
                // TODO 压缩成功后调用，返回压缩后的图片文件
                Toast.makeText(context,"压缩成功 = " + (file.listFiles() == null ? "file is null": file.listFiles().length),Toast.LENGTH_SHORT).show();
                Log.e(TAG," 压缩成功  file = " + file);
            }

            @Override
            public void onError (Throwable e){
                Log.e(TAG,"压缩失败。。。。");
                Toast.makeText(context,"压缩失败 " + e.getMessage(),Toast.LENGTH_SHORT).show();
                // TODO 当压缩过程出现问题时调用
            }
        }).launch();
    }
}
