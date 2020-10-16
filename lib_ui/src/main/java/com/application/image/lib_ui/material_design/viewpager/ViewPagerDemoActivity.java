package com.application.image.lib_ui.material_design.viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.application.image.lib_ui.R;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by yuhongwen
 * on 2020/10/15
 *  1 ： ViewPager + Fragment + tabLayout + RadioButton 结合使用
 *  2: ：Fragment 生命周期
 *  3：Fragment切换
 *  4：Fragment 传递数据
 */
public class ViewPagerDemoActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity);
        tabLayout = findViewById(R.id.tablayout_settings);
        viewPager = findViewById(R.id.vp_settings_pager);
        initFragment();
    }

    private void initFragment() {
        HashMap<String, Fragment> fragmentMap = new LinkedHashMap<>();
        fragmentMap.put("设备信息",new BasicSettingFragment());
        fragmentMap.put("设备状态",new StateSettingFragment());
        fragmentMap.put("报警",new AlarmFragment());
        fragmentMap.put("网络",new NetworkFragment());
        fragmentMap.put("离线语音",new AudioSettingFragment());
        fragmentMap.put("人脸库",new FaceLibFragment());
        fragmentMap.put("系统配置",new SystemSettingFragment());

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), fragmentMap);
        // TODO 这个参数的设置可以保证ViewPager在滑动的时候 加载的 Fragment个数
        //  How many pages will be kept offscreen in an idle state
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
