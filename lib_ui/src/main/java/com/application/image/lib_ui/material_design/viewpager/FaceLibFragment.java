package com.application.image.lib_ui.material_design.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.viewpager.face.FaceListFragment;

/**
 * Created by yuhongwen
 * on 2020/10/15
 */
public class FaceLibFragment extends androidx.fragment.app.Fragment implements RadioGroup.OnCheckedChangeListener{

    private static final String FACE_WHITE = "face_white";
    private static final String FACE_BLACK = "face_black";
    private static final String FACE_STAFF = "face_staff";
    private View mView;
    private boolean isPerformOnViewCreate;

    private FragmentManager fragmentManager;

    private RadioButton rbtnWhiteFace;

    private RadioGroup rgs;

    private FaceListFragment whiteFragment;
    private FaceListFragment blackFragment;
    private FaceListFragment staffFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceLibFragment onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceLibFragment onViewCreated");
        if (!isPerformOnViewCreate) {
            return;
        }
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if(getActivity() == null) {
            return;
        }
        fragmentManager = getActivity().getSupportFragmentManager();
        rgs.setOnCheckedChangeListener(this);
        rbtnWhiteFace.setChecked(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceLibFragment onCreateView");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_face_lib, container, false);
            isPerformOnViewCreate = true;
        } else {
            isPerformOnViewCreate = false;
        }
        return mView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        if (checkedId == R.id.rbtn_white_face) {
            if (whiteFragment == null) {
                whiteFragment = FaceListFragment.newInstance(FACE_WHITE);
                transaction.add(R.id.main_container, whiteFragment);
            } else {
                transaction.show(whiteFragment);
                whiteFragment.onLoadMore();
            }
        } else if (checkedId == R.id. rbtn_black_face) {
            if (blackFragment == null) {
                blackFragment = FaceListFragment.newInstance(FACE_BLACK);
                transaction.add(R.id.main_container, blackFragment);
            } else {
                transaction.show(blackFragment);
                blackFragment.onLoadMore();
            }
        } else if (checkedId == R.id.rbtn_staff_face) {
            if (staffFragment == null) {
                staffFragment = FaceListFragment.newInstance(FACE_STAFF);
                transaction.add(R.id.main_container, staffFragment);
            } else {
                transaction.show(staffFragment);
                staffFragment.onLoadMore();
            }
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (whiteFragment != null) {
            transaction.hide(whiteFragment);
        }
        if (blackFragment != null) {
            transaction.hide(blackFragment);
        }
        if (staffFragment != null) {
            transaction.hide(staffFragment);
        }
    }
}
