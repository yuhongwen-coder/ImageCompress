package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.b;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dou361.dialogui.DialogUIUtils;
import com.maxvision.robot.DbControllerFactory;
import com.maxvision.robot.interfaces.OnOperationListener;
import com.maxvision.tech.common.utils.CustomToast;
import com.maxvision.tech.common.view.CustomDialog;
import com.maxvision.tech.robot.R;
import com.maxvision.tech.ros.RosSendMessage;
import com.maxvision.tech.ros.entity.FaceUploadOrDelData;
import com.maxvision.tech.ros.observer.ObserverHandler;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * name: wy
 * date: 2020/9/25
 * desc: 人脸上传
 */
public class FaceLibFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, FaceListFragment.DialogOnSureListener {

    //白名单
    public static final String FACE_WHITE = "1";
    //黑名单
    public static final String FACE_BLACK = "2";
    //员工名单
    public static final String FACE_STAFF = "3";

    private Unbinder unbinder;
    private View mView;
    private boolean isPerformOnViewCreate = true;

    @BindView(R.id.rbs)
    RadioGroup rgs;
    @BindView(R.id.rbtn_white_face)
    RadioButton rbtnWhiteFace;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    private FragmentManager fragmentManager;
    private FaceListFragment whiteFragment;
    private FaceListFragment blackFragment;

    private static final String TAG = "FaceLibFragment";

    /**
     * 员工名单
     */
    private FaceListFragment staffFragment;
    private Disposable disposable;
    private Dialog mDialogUIUtils;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView enter");
        Log.e("yhw","FaceLibFragment onCreateView  = ");
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_face_lib, container, false);
            isPerformOnViewCreate = true;
        } else {
            isPerformOnViewCreate = false;
        }
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onViewCreated enter");
        Log.e("yhw","FaceLibFragment onViewCreated  = ");
        if (!isPerformOnViewCreate) return;
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if(getActivity() == null) return;
        fragmentManager = getActivity().getSupportFragmentManager();
        rgs.setOnCheckedChangeListener(this);
        rbtnWhiteFace.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (checkedId) {
            case R.id.rbtn_white_face:
                if (whiteFragment == null) {
                    whiteFragment = FaceListFragment.newInstance(FACE_WHITE);
                    whiteFragment.setDialogOnSureListener(this);
                    transaction.add(R.id.main_container, whiteFragment);
                } else {
                    transaction.show(whiteFragment);
                    whiteFragment.onLoadMore();
                }
                break;
            case R.id.rbtn_black_face:
                if (blackFragment == null) {
                    blackFragment = FaceListFragment.newInstance(FACE_BLACK);
                    blackFragment.setDialogOnSureListener(this);
                    transaction.add(R.id.main_container, blackFragment);
                } else {
                    transaction.show(blackFragment);
                    blackFragment.onLoadMore();
                }
                break;
            case R.id.rbtn_staff_face:
                if (staffFragment == null) {
                    staffFragment = FaceListFragment.newInstance(FACE_STAFF);
                    staffFragment.setDialogOnSureListener(this);
                    transaction.add(R.id.main_container, staffFragment);
                } else {
                    transaction.show(staffFragment);
                    staffFragment.onLoadMore();
                }
                break;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    /**
     * 清除所有
     * @param view
     */
    @OnClick({R.id.tv_clear})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_clear) {
            // 不存在人脸信息
            if (getFaceListSize() == 0) {
                CustomToast.toastLong(CustomToast.TIP_SUCCESS, getString(R.string.no_face_data));
                return;
            }
            CustomDialog customDialog = new CustomDialog.Builder(getContext())
                    .setContent(getResources().getString(R.string.text_hint_all_delface))
                    .setOnSureListener((dialog, content) -> {
                        showDialog(1);
                        deleteAllFaceDate();
                    })
                    .build();
            customDialog.show();
        }
    }


    private void deleteAllFaceDate() {
        RosSendMessage.getInstance().deleteAllFace(new ObserverHandler<FaceUploadOrDelData>() {
            @Override
            public void onSuccess(FaceUploadOrDelData data) {
                DbControllerFactory.getInstance().getFaceListDataController().deleteAllFace(new OnOperationListener() {
                    @Override
                    public void onOperationResult(boolean state, Object object) {
                        Log.d(TAG,"onOperationResult state = " + state);
                        if (state) {
                            if (whiteFragment != null) {
                                whiteFragment.delFaceAll();
                            }
                            if (blackFragment != null) {
                                blackFragment.delFaceAll();
                            }
                            if (staffFragment != null) {
                                staffFragment.delFaceAll();
                            }
                            CustomToast.toastLong(CustomToast.TIP_SUCCESS, getString(R.string.delete_success));
                        }
                    }
                });
            }

            @Override
            public void onError(String code) {
                CustomToast.toastLong(CustomToast.TIP_SUCCESS, getString(R.string.delete_fail));
            }
        });
    }

    private void dispose() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void showDialog(int type) {
        String content;
        switch (type) {
            case 1:
                content = getString(R.string.text_delete_all_face_loading);
                break;
            case 2:
                content = getString(R.string.text_delete_white_list_loading);
                break;
            case 3:
                content = getString(R.string.text_delete_black_list_loading);
                break;
            case 4:
                content = getString(R.string.text_delete_employee_list_loading);
                break;
            case 5:
                content = getString(R.string.text_upload_face_info_loading);
                break;
            default:
                content = getString(R.string.set_request_loading);
                break;
        }
        mDialogUIUtils = DialogUIUtils.showLoading(getActivity(), content, true, false, false, true).show();
        dispose();
        disposable = Observable.timer(10, TimeUnit.SECONDS).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(aLong -> diss());
    }

    @Override
    public void diss() {
        if (mDialogUIUtils != null && mDialogUIUtils.isShowing()) {
            mDialogUIUtils.dismiss();
        }
        dispose();
    }

    private long getFaceListSize() {
        return DbControllerFactory.getInstance().getFaceListDataController().getAllSize();
    }

}