package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;


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
        final FaceCaptureAdapter itemAdapter = new FaceCaptureAdapter(mContext);
        Observer<PagedList<FaceAlarm>> observer = new Observer<PagedList<FaceAlarm>>() {
            @Override
            public void onChanged(PagedList<FaceAlarm> pagedList) {
                itemAdapter.submitList(pagedList);
            }
        };
//        mAlarmTableModel.getFaceCaptureData(GlobalStates.mRobotSN).observe(this,observer);
        rlFaceCapature.setAdapter(itemAdapter);

    }
}
