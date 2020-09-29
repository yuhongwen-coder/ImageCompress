package com.application.image.lib_download.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_download.R;

/**
 * Created by yuhongwen
 * on 2020/9/29
 */
public class DownLoadMoudelActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_moudel_layout);
        findViewById(R.id.single_app_download).setOnClickListener(this);
        findViewById(R.id.multi_app_download).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        if (id ==R.id.single_app_download) {
            intent.setClass(this,MainActivity.class);
        } else if (id == R.id.multi_app_download) {
            intent.setClass(this,ListActivity.class);
        }
        startActivity(intent);
    }
}
