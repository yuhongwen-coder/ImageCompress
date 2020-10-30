package com.application.image.lib_ui.material_design.viewpager.adapter.pageradapter;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by yuhongwen
 * on 2020/10/27
 */
public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
