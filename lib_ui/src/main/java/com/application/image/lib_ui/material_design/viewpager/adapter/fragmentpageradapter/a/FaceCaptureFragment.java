package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maxvision.tech.robot.GlobalStates;
import com.maxvision.tech.robot.R;
import com.maxvision.tech.robot.adapter.FaceCaptureAdapter;
import com.maxvision.tech.robot.db.alarm.FaceAlarm;
import com.maxvision.tech.robot.ui.model.AlarmTableModel;

import butterknife.BindView;

/**
 * author: glc.
 * Date: 2019/12/24 10:10
 * Description:
 *
 * 人脸抓拍
 */
public class FaceCaptureFragment extends BaseFragment {

    RecyclerView rlFaceCapature;

    private Context mContext;
    private AlarmTableModel mAlarmTableModel;

    public static FaceCaptureFragment getInstance() {
        FaceCaptureFragment fragment = new FaceCaptureFragment();
        return fragment;
    }

    @Override
    public int getInflateViewSource() {
        return R.layout.fragment_face_capture;
    }

    @Override
    public void initData(AlarmTableModel alarmTableModel, Context context) {
        this.mContext = context;
        this.mAlarmTableModel = alarmTableModel;
        rlFaceCapature.setLayoutManager(new LinearLayoutManager(mContext));
        FaceCaptureAdapter itemAdapter = new FaceCaptureAdapter(mContext);
        Observer<PagedList<FaceAlarm>> observer = itemAdapter::submitList;
        mAlarmTableModel.getFaceCaptureData(GlobalStates.mRobotSN).observe(this,observer);
        rlFaceCapature.setAdapter(itemAdapter);

    }
}
