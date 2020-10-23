package com.application.image.lib_ui.material_design.status;

import android.net.TrafficStats;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: glc.
 * Date: 2019/12/4 15:56
 * Description:
 */
public class NetSpeed {

    private Disposable mSubscribe;

    private NetSpeed(){}
    private static class NetSpeedInstance{
        private static final NetSpeed instance = new NetSpeed();
    }
    public static NetSpeed getInstance(){
        return NetSpeed.NetSpeedInstance.instance;
    }

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public float getNetSpeed(int uid) {
        long nowTotalRxBytes =
        TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
        long nowTimeStamp = System.currentTimeMillis();
        float speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000f / (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return speed;
    }

    public void start(final OnNetSpeedListener speedListener){
        if (mSubscribe != null && mSubscribe.isDisposed()){return;}
        int uid = 1;
//        mSubscribe = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
//                .map(new Function<Integer, Float>() {
//                    @Override
//                    public Float apply(Integer aLong) throws Exception {
//                        return getNetSpeed((int)aLong);
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Float>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Float speed) {
//                        if (speedListener != null) {
//                            speedListener.onNetSpeed(cavertSpeed(speed));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


    }

    public void stop(){
        if (mSubscribe != null && mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }

    private String cavertSpeed(float speed){
        DecimalFormat df = new DecimalFormat("#0.0");
        String spe = "";
        if (speed < 1024) {
            spe = df.format(speed) + "kb/s";
        }else if (speed >= 1024){
            spe = df.format(speed / 1024f) + "mb/s";
        }
        return spe;
    }

    public interface OnNetSpeedListener{
        void onNetSpeed(String s);
    }
}
