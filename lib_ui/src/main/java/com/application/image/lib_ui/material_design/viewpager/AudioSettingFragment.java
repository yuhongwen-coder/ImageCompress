package com.application.image.lib_ui.material_design.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by yuhongwen
 * on 2020/10/15
 */
public class AudioSettingFragment extends androidx.fragment.app.Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("yhw","AudioSettingFragment onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","AudioSettingFragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","AudioSettingFragment onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
