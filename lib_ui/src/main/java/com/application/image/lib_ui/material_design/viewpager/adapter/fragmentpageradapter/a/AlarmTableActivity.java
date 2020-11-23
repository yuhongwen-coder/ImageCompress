package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.dou361.dialogui.DialogUIUtils;
import com.maxvision.tech.robot.GlobalStates;
import com.maxvision.tech.robot.R;
import com.maxvision.tech.robot.bean.AlarmTypeModel;
import com.maxvision.tech.robot.ui.BaseActivity;
import com.maxvision.tech.robot.ui.alarm.tablayout.CustomScrollViewPager;
import com.maxvision.tech.robot.ui.alarm.tablayout.VerticalTabLayout;
import com.maxvision.tech.robot.ui.model.AlarmTableModel;
import com.maxvision.tech.robot.utils.DataFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 报警列表
 */
public class AlarmTableActivity extends BaseActivity {

    @BindView(R.id.tab_pager) CustomScrollViewPager tabPager;
    @BindView(R.id.tab_layout) VerticalTabLayout tabLayout;
    @BindView(R.id.back) ImageView ivBack;
    private AlarmTableModel mAlarmTableModel;
    private List<AlarmTypeModel> mAlarmTypeModels = new ArrayList<>();
    private int mFragmentFlag;

    public static void startActivity(Context context,int type){
        Intent intent = new Intent(context,AlarmTableActivity.class);
        intent.putExtra("defaultType",type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("glc", "getAlarmTable onCreate: ");
        setContentView(R.layout.activity_alarm_table);
        ButterKnife.bind(this);
        ivBack.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        mFragmentFlag = intent.getIntExtra("defaultType", -1);
        mAlarmTableModel = ViewModelProviders.of(this).get(AlarmTableModel.class);
        getAlarmTable();
    }

    @SuppressLint("CheckResult")
    private void getAlarmTable() {
        List<AlarmTypeModel> alarmTypeModel = GlobalStates.getInstance().getAlarmTypeModel();
        if (alarmTypeModel != null && alarmTypeModel.size()>0) {
            mAlarmTypeModels.addAll(alarmTypeModel);
            showFragment();
            return;
        }
        showRequestDialog(REQUEST_DIALOG);
        Log.i("glc", "getAlarmTable 获取机器人报警列表enter: ");
        mAlarmTableModel.getAlarmTableList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // [{"title":"人脸识别","type":100},{"title":"温度检测","type":102},{"title":"口罩检测","type":103},{"title":"区域布控","type":101}]
                .subscribe(s -> {
                    hindLoading();
                    Log.i("glc", "getAlarmTable 获取机器人报警列表 end: " + s);
                    List<AlarmTypeModel> alarmTypeModels = DataFactory.jsonToArrayList((String) s, AlarmTypeModel.class);
                    Log.i("glc", "alarmTypeModels: "+ alarmTypeModels == null ? "is null" : alarmTypeModels.size()+"");
                    GlobalStates.getInstance().setAlarmTypeModels(alarmTypeModels);
                    mAlarmTypeModels.addAll(alarmTypeModels);
                    showFragment();
                }, throwable -> {
                    Log.i("glc", "getAlarmTable 获取机器人报警列表 throwable: " + throwable);
                    DialogUIUtils.showToastCenterLong(R.string.request_error);
                    hindLoading();
                });
    }

    private void showFragment() {
        Set<AlarmTypeModel> setData = new HashSet<AlarmTypeModel>();
        setData.addAll(mAlarmTypeModels);
        AlarmFragmentAdapter alarmFragmentAdapter =
                new AlarmFragmentAdapter(getSupportFragmentManager(),setData , this);
        Log.i("glc", "showFragment setAdapter before");
        tabPager.setAdapter(alarmFragmentAdapter);
        Log.i("glc", "showFragment setAdapter after");
        tabLayout.setupWithViewPager(tabPager);
        tabLayout.setTabSelected(mFragmentFlag);
    }
}
