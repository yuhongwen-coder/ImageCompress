package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;


/**
 * author: glc.
 * Date: 2019/12/24 10:42
 * Description:
 */
public abstract class BaseFragment extends Fragment {

    private Context mContext;
    private FragmentActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        mActivity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getInflateViewSource(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlarmTableModel mAlarmTableModel = ViewModelProviders.of(mActivity).get(AlarmTableModel.class);
        initData(mAlarmTableModel,mContext);
    }

    public abstract int getInflateViewSource();

    public abstract void initData(AlarmTableModel alarmTableModel,Context context);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
