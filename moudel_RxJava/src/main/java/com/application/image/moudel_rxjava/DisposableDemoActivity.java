package com.application.image.moudel_rxjava;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
}