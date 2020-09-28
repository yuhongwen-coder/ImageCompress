package com.application.image.compress;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_log.LogUtilsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by yuhongwen
 * on 2020/9/10
 */
public class DemoActivity extends AppCompatActivity {
    private static final String TAG = "DemoActivity";
    private TextView compressImage;
    private List<String> listImagePath;
    private ImageView showImage;
    private TextView jumpLog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},1);
        compressImage = findViewById(R.id.compress_image);
        jumpLog = findViewById(R.id.jump_log);
        showImage = findViewById(R.id.show_image);
        jumpLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DemoActivity.this, LogUtilsActivity.class);
                startActivity(intent);
            }
        });
        compressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 将图片原样压缩
//                String srcPath = Environment.getExternalStorageDirectory() + "/" + "Robot2"+"/" + "alarm_blacklist";
//                listImagePath = SdcardImage.getSdcardImagePath(srcPath);
//                String destImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2";
//                String  srcImagePath;
//                for (int i = 0;i<listImagePath.size();i++) {
//                    srcImagePath = listImagePath.get(i);
//                    Log.e(TAG,"srcImagePath = " + srcImagePath);
////                String srcImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2"+"/" + "alarm_dw";
//                    ImageCompressUtils.compressImage(DemoActivity.this, srcImagePath, destImagePath);
//                }
////                String srcImagePath = listImagePath.get(2);+

                String srcImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2"+"/" + "alarm_dw";

                String destImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2";
                ImageCompressUtils.compressImage(DemoActivity.this, srcImagePath, destImagePath);
//                File file = new File(srcImagePath);
//                try {
//                    InputStream inputStream = new FileInputStream(file);
//                    customCompressImage(inputStream);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                customCompressImage0000(file);
//                String destImage = Environment.getExternalStorageDirectory() + "/Robot2";
//                ImageCompressUtils.compressImage(DemoActivity.this, srcImagePath, destImage);

//                customCompressImage();
            }
        });
    }

    private void customCompressImage0000(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while(true){
                try {
                    if (!((len = inputStream.read(buffer)) != -1)) break;
                } catch (IOException e) {

                }
                String str = new String(buffer,0,len);
                System.out.println(str);
            }
            //4 关闭文件
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void customCompressImage(InputStream inputStream) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        showImage.setImageBitmap(bitmap);

//        Bitmap tagBitmap = BitmapFactory.decodeStream(srcImg.open(), null, BitmapFactory.Options);
    }
}
