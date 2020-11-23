package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxvision.tech.robot.GlobalStates;
import com.maxvision.tech.robot.R;
import com.maxvision.tech.robot.adapter.BlackListAdapter;
import com.maxvision.tech.robot.db.alarm.BlackListAlarm;
import com.maxvision.tech.robot.ui.model.AlarmTableModel;

import butterknife.BindView;

/**
 * author: glc.
 * Date: 2019/12/24 10:11
 * Description:
 */
public class BlackAlarmListFragment extends BaseFragment {

    @BindView(R.id.rl_black_view)
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
        BlackListAdapter listAdapter = new BlackListAdapter(mContext);
        Observer<PagedList<BlackListAlarm>> observer = listAdapter::submitList;
        mAlarmTableModel.getBlacklistData(GlobalStates.mRobotSN).observe(this,observer);
        rlBlackView.setAdapter(listAdapter);
    }
}
