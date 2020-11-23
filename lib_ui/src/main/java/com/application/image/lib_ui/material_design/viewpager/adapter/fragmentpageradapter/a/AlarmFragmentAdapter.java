package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.maxvision.tech.robot.R;
import com.maxvision.tech.robot.bean.AlarmTypeModel;
import com.maxvision.tech.robot.constant.Function;
import com.maxvision.tech.robot.ui.alarm.tablayout.ITabView;
import com.maxvision.tech.robot.ui.alarm.tablayout.QTabView;
import com.maxvision.tech.robot.ui.alarm.tablayout.TabAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlarmFragmentAdapter extends FragmentPagerAdapter implements TabAdapter {

    private Context context;
    private Set<AlarmTypeModel> mTypeModels;
    private Map<String,BaseFragment> mTypeFragment = new LinkedHashMap<>();
    private List<String> mTitlees = new ArrayList<>();

    public AlarmFragmentAdapter(FragmentManager fm, Set<AlarmTypeModel> typeModels, Context context) {
        super(fm);
            mTypeModels = typeModels;
        this.context = context;
        initFragment();
    }

    private void initFragment() {
        for (AlarmTypeModel mTypeModel : mTypeModels) {
            String title = mTypeModel.getTitle();
            int robotFunType = mTypeModel.getType();
            if (robotFunType == Function.FUNCTION_MONITOR_PAD_ROBOT){
                mTypeFragment.put(title,AreaMonitorFragment.getInstance());
                mTitlees.add(title);
            }else if (robotFunType == Function.FUNCTION_DETECTION_PAD_ROBOT){
                mTypeFragment.put(title,LowTempFragment.getInstance());
                mTitlees.add(title);
            }else if (robotFunType == Function.FUNCTION_FACE_PAD_ROBOT){
                mTypeFragment.put(title,BlackAlarmListFragment.getInstance());
                mTitlees.add(title);
            }else if (robotFunType == Function.FUNCTION_WDJC_PAD_ROBOT){
                mTypeFragment.put(title,TemperatureFragment.getInstance());
                mTitlees.add(title);
            } else if (robotFunType == Function.ALARM_KZJC_PAD_ROBOT) {
                mTypeFragment.put(title,KzjcAlarmListFragment.getInstance());
                mTitlees.add(title);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("glc", "showFragment getItem");
        String title = mTitlees.get(position);
        return mTypeFragment.get(title);
    }

    @Override
    public int getCount() {
        Log.i("glc", "showFragment getCount");
        return mTitlees.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new QTabView.TabTitle.Builder()
                .setContent(mTitlees.get(position))
                .setTextSize(20)
                .setTextBgColor(context.getColor(R.color.text_yellow90), context.getColor(android.R.color.transparent))
                .setTextColor(context.getColor(R.color.text_yellow), context.getColor(R.color.zz_white))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return android.R.color.transparent;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlees.get(position);
    }
}
