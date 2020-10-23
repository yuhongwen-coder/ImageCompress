package com.application.image.lib_ui.material_design.status;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.utils.ReflectContext;


/**
 * Author: ml
 * Date: 2019/5/9 13:57
 * Description: 状态栏
 */
public class StatusView extends LinearLayout {

    private static final String TAG = "StatusView";

    private TextView tvNet;
    private ImageView ivNet;
    private TextView tvBattery;
    private ImageView ivBattery;
    private TextClock tvTime;
//    private TextView tvDate;
    private String net;
    private String time;
    private TextView netSpeed;

    private AnimationDrawable batteryAnim;

    private ConnectivityManager connectivityManager;
//    private DateChangeReceiver receiver;
    private ImageView icRosError;
    private ImageView icNetError;

    public StatusView(Context context) {
        this(context, null, 0);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        connectivityManager = (ConnectivityManager) ReflectContext.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View myView = mInflater.inflate(R.layout.layout_status_view, null);

        tvNet = myView.findViewById(R.id.tv_net);
        ivNet = myView.findViewById(R.id.iv_net);
        tvBattery = myView.findViewById(R.id.tv_battery);
        ivBattery = myView.findViewById(R.id.iv_battery);
        tvTime = myView.findViewById(R.id.tv_time);
//        tvDate = myView.findViewById(R.id.tv_date);
        netSpeed = myView.findViewById(R.id.net_speed);
        icRosError = myView.findViewById(R.id.iv_ros_error);
        icNetError = myView.findViewById(R.id.ic_net_error);

        // 2019/11/27 屏蔽多语言
//        receiver = new DateChangeReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_TIME_TICK);
//        getContext().registerReceiver(receiver, intentFilter);
//        receiver.setListener(day -> showView());
        showView();
        addView(myView);

//        checkNetwork();
//        pingBaidu();

        showNetSpeed(); //add  2019/12/4  glc
    }

    public void showView() {
        net = "Internet";
        tvNet.setText(net);
    }

    private String getDateEn(String day) {
        switch (day) {
            case "星期一":
                return "Monday";
            case "星期二":
                return "Tuesday";
            case "星期三":
                return "Wednesday";
            case "星期四":
                return "Thursday";
            case "星期五":
                return "Friday";
            case "星期六":
                return "Saturday";
            case "星期天":
                return "Sunday";
        }
        return "Monday";
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG,"onAttachedToWindow=");
        //checkNetwork();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG,"onDetachedFromWindow=");
        //unregisterNetwork();
//        if (receiver != null) {
//            getContext().unregisterReceiver(receiver);
//        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        Log.i(TAG,"onWindowVisibilityChanged="+visibility);
        if(visibility == View.VISIBLE){
            ivNet.setImageResource(R.drawable.ic_net_four);
            showCurBatteryImage(20);
            int curState = 40;
            startChargeAnim();
        }
    }


    public void rosState(int visible){
        icRosError.setVisibility(visible);
    }

    public void netState(int visible){
        icNetError.setVisibility(visible);
    }

    /**
     * 反注册ros
     */
    public void unregisterRos(){
        //ObserverManager.getInstance().unregister(this);
    }

    public void setTvBattery(int bat){
//        tvBattery.setText("电量："+bat+"|"+System.currentTimeMillis());
        showCurBatteryImage(bat);
    }




    /*@Override
    public void onRosEvent(Data object) {
        if(null == object)return;
        if(object.code == Data.OK) {
            switch (object.topicName) {
                case TopicConstants.ROBOT_BATTARY:
                    int curState = RobotBaseApplication.getInstance().getIntVariable(StaticConstants.CURRENT_SPORT_STATE,0);
                    if(curState == SportState.CHARGING)return;
                    BattaryData battaryData = (BattaryData) object;
                    Log.i(TAG, "onRosEvent="+battaryData.bat);
                    //测试用，显示电量具体数值
                    tvBattery.post(() -> {
                        tvBattery.setText("电量："+battaryData.bat+"|"+System.currentTimeMillis());
                    });
                    showCurBatteryImage(battaryData.bat);
                    break;
                case TopicConstants.ROBOT_TIPS:
                    TipsData tipsData = (TipsData)object;
                    if(tipsData.type == 1 && tipsData.data == 5){
                        startChargeAnim();
                    }else {
                        stopChargeAnim();
                    }
                    break;
            }
        }
    }*/

    /*ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            Log.i(TAG, "sssonAvailable=");
            pingBaidu();
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            Log.i(TAG, "sssonLost=");
            pingBaidu();
        }
    };*/

    /*private void unregisterNetwork(){
        if(null == connectivityManager || null == networkCallback)return;
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }*/

    /**
     * 监听网络状态变化
     */
    /*private void checkNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(null == connectivityManager)return;
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build();
            connectivityManager.requestNetwork(request, networkCallback);
        }
    }*/

    /**
     * 判断互联网是否可用
     */
    /*public void pingBaidu() {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Thread.sleep(1000);
            //emitter.onNext(PingUtil.ping("119.29.29.29"));
            //emitter.onNext(PingUtil.ping("www.baidu.com"));
            emitter.onNext(PingUtil.ping("www.qq.com"));
            emitter.onComplete();
        }).timeout(3000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.i(TAG, "sssonNext=" + aBoolean);
                RobotBaseApplication.getInstance().setNetworkState(aBoolean);
                if(aBoolean){
                    ivNet.post(() -> ivNet.setImageResource(R.drawable.ic_net_four));
                }else {
                    ivNet.post(() -> ivNet.setImageResource(R.drawable.ic_net_none));
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "sss=onError");
                RobotBaseApplication.getInstance().setNetworkState(false);
                ivNet.post(() -> ivNet.setImageResource(R.drawable.ic_net_none));
                pingBaidu();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "sss=onComplete");
            }
        });

    }*/

    // 显示电量图标
    private void showCurBatteryImage(int curBattery) {
        Log.i(TAG,"ivBattery="+curBattery);
        if(curBattery == -1){
            tvBattery.setText("");
        }else {
            tvBattery.setText(curBattery+"%");
        }
        if(isAnimate){
            return;
        }
        if (curBattery >= 0 && curBattery <= 20) {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_one);
        } else if (curBattery > 20 && curBattery <= 40) {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_two);
        } else if (curBattery > 40 && curBattery <= 60) {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_three);
        } else if (curBattery > 60 && curBattery <= 80) {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_four);
        } else if (curBattery > 80 && curBattery <= 100) {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_five);
        } else {
            ivBattery.setBackgroundResource(R.drawable.ic_battery_none);
        }
    }

    private boolean isAnimate;

    private void startChargeAnim(){
        isAnimate = true;
        ivBattery.setBackgroundResource(R.drawable.charge_anim);
        batteryAnim = (AnimationDrawable) ivBattery.getBackground();
        batteryAnim.setOneShot(false);
        batteryAnim.start();
    }

    private void stopChargeAnim(){
        if (null != batteryAnim && batteryAnim.isRunning()) {
            batteryAnim.stop();
        }
        isAnimate = false;
        showCurBatteryImage(30);
    }


    private void showNetSpeed(){
//        NetSpeed.getInstance().start(f -> netSpeed.setText(f));

    }
}
