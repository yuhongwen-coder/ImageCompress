package com.application.image.moudel_rxjava;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuhongwen
 * on 2020/10/21
 */
public class DisposableDemoActivity extends AppCompatActivity {


    private Disposable disposable;
    private Disposable delayDisposable;
    private Disposable taskDp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void pingBaidu() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(PingUtil.ping("www.alibaba.com"));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).map(new Function<Boolean, Object>() {
            @Override
            public Object apply(Boolean aBoolean) throws Exception {
                if (aBoolean) return true;
                return PingUtil.ping("www.baidu.com");
            }
        }).subscribeOn(Schedulers.io()).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object aBoolean) throws Exception {
                return PingUtil.ping("www.baidu.com");
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object aBoolean) throws Exception {

                    }
                });
    }

    /**
     * 实现了延时执行代码（任务功能）
     */
    private void delayExecuteTask() {
        if (delayDisposable != null && !delayDisposable.isDisposed()) {
            delayDisposable.dispose();
        }
        delayDisposable = Observable.timer(2,TimeUnit.MINUTES)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
    }


    public void pollTask(String taskId) {
        disposeTask();
        taskDp = Observable.interval(60, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
            }
        });
    }

    private void disposeTask() {
        if (taskDp != null && !taskDp.isDisposed()) {
            taskDp.dispose();
        }
    }



}
