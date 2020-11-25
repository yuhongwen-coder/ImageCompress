package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;


/**
 * author: glc.
 * Date: 2019/12/20 16:46
 * Description:
 */
public class AreaMonitorFragment extends BaseFragment {


    private AlarmTableModel mAlarmTableModel;
    private Context mContext;

    public static AreaMonitorFragment getInstance() {
        AreaMonitorFragment fragment = new AreaMonitorFragment();
        return fragment;
    }

    @Override
    public int getInflateViewSource() {
        return R.layout.fragment_area_monitor;
    }

    @Override
    public void initData(AlarmTableModel alarmTableModel, Context context) {
        this.mAlarmTableModel = alarmTableModel;
        this.mContext = context;
//        rvAreaMonitor.setLayoutManager(new GridLayoutManager(mContext,3));
//        rvAreaMonitor.addItemDecoration(new ItemDecoration(DensityUtil.dip2px(mContext,20)));
//        AreaMontiorAdapter areaMontiorAdapter = new AreaMontiorAdapter(mContext);
//        Observer observer = (Observer<PagedList<AreaMontiorAlarm>>) areaMontiorAdapter::submitList;
//        mAlarmTableModel.getAreaMonitorData(GlobalStates.mRobotSN).observe(this,observer);
//        areaMontiorAdapter.setOnItemClickListener((view, strings) -> new ImageViewer(getActivity(),strings).show());
//        rvAreaMonitor.setAdapter(areaMontiorAdapter);
    }
}
