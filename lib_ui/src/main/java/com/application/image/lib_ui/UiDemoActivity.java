package com.application.image.lib_ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_ui.material_design.custom.CustomViewActivity;
import com.application.image.lib_ui.material_design.dialog.DialogActivity;
import com.application.image.lib_ui.material_design.popupwindow.PopupWindowDemoActivity;
import com.application.image.lib_ui.material_design.recylerview.RecylerViewTabActivity;
import com.application.image.lib_ui.material_design.swip.SwipeRefreshLayoutActivity;
import com.application.image.lib_ui.material_design.viewpager.ViewPagerDemoActivity;

/**
 * Created by yuhongwen
 * on 2020/9/29
 */
public class UiDemoActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_demo_activity);
        findViewById(R.id.swiprehresh_demo).setOnClickListener(this);
        findViewById(R.id.recylerview_demo).setOnClickListener(this);
        findViewById(R.id.viewpager_demo).setOnClickListener(this);
        findViewById(R.id.popupwindow_demo).setOnClickListener(this);
        findViewById(R.id.dialogfragment_demo).setOnClickListener(this);
        findViewById(R.id.custom_view_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        if (id == R.id.swiprehresh_demo) {
            intent.setClass(this, SwipeRefreshLayoutActivity.class);
        } else if (id ==R.id.recylerview_demo) {
            intent.setClass(this, RecylerViewTabActivity.class);
        } else if (id ==R.id.viewpager_demo) {
            intent.setClass(this, ViewPagerDemoActivity.class);
        } else if (id ==R.id.popupwindow_demo) {
            intent.setClass(this, PopupWindowDemoActivity.class);
        } else if (id ==R.id.dialogfragment_demo) {
            intent.setClass(this, DialogActivity.class);
        } else if (id == R.id.custom_view_demo) {
            intent.setClass(this, CustomViewActivity.class);
        }
        startActivity(intent);
    }
}
