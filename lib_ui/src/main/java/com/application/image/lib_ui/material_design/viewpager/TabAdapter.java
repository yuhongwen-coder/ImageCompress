package com.application.image.lib_ui.material_design.viewpager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/10/15
 */
public class TabAdapter extends FragmentPagerAdapter {

    //fragment列表
    private List<Fragment> list_fragment = new ArrayList<>();
    //tab名的列表
    private List<String> list_Title = new ArrayList<>();

    public TabAdapter(FragmentManager fm, HashMap<String,Fragment> hashMap) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        for (String name : hashMap.keySet()) {
            list_fragment.add(list_fragment.size(),hashMap.get(name));
            list_Title.add(list_Title.size(),name);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.e("yhw","getItem position = " + position);
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        Log.e("yhw","getItem getCount = " + list_Title.size());
        return list_fragment.size();
    }

    public CharSequence getPageTitle(int position) {
        Log.e("yhw","getPageTitle String = " + list_Title.get(position % list_Title.size()));
        return list_Title.get(position % list_Title.size());
    }
}
