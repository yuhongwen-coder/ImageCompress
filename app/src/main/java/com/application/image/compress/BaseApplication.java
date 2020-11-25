package com.application.image.compress;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by yuhongwen
 * on 2020/9/14
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        instance = this;
        initCrashLogToFile();
        MultiDex.install(this);
    }

    private void initCrashLogToFile() {
        // 将Crash 日志保存到 手机sdcard中
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }


    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // 是否显示线程信息，默认为ture
                .methodCount(3)         // 显示的方法行数，默认为2
                .methodOffset(7)        // 隐藏内部方法调用到偏移量，默认为5
                .tag("My custom tag")   // 每个日志的全局标记。默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
//        FormatStrategy formatStrategyDisk = CsvFormatStrategy.newBuilder()
//                .tag("custom")
//                .build();
//        Logger.addLogAdapter(new DiskLogAdapter(formatStrategyDisk));
    }
}
