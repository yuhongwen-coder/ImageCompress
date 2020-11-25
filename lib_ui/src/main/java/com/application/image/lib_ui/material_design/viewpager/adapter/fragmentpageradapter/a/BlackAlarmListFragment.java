package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;


/**
 * author: glc.
 * Date: 2019/12/24 10:11
 * Description:
 */
public class BlackAlarmListFragment extends BaseFragment {

    RecyclerView rlBlackView;

    private Context mContext;
    private AlarmTableModel mAlarmTableModel;

    public static BlackAlarmListFragment getInstance() {
        BlackAlarmListFragment fragment = new BlackAlarmListFragment();
        return fragment;
    }

    @Override
    public int getInflateViewSource() {
        return R.layout.fragment_black_alarm_list;
    }

    @Override
    public void initData(AlarmTableModel alarmTableModel, Context context) {
        this.mContext = context;
        this.mAlarmTableModel = alarmTableModel;
        rlBlackView.setLayoutManager(new LinearLayoutManager(mContext));
//        BlackListAdapter listAdapter = new BlackListAdapter(mContext);
//        Observer<PagedList<BlackListAlarm>> observer = listAdapter::submitList;
//        mAlarmTableModel.getBlacklistData(GlobalStates.mRobotSN).observe(this,observer);
//        rlBlackView.setAdapter(listAdapter);
    }
}
