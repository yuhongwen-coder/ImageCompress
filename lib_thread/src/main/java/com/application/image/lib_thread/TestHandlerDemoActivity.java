package com.application.image.lib_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by yuhongwen
 * on 2020/9/28
 *   handler 用于在 主线程放数据，子线程处理数据，然后将处理好的数据，发送给主线程
 */
public class TestHandlerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler_layout);
        putData();
    }

    private static final int INIT_SIZE = 10;
    private static BlockingDeque<String> blockingDeque = new LinkedBlockingDeque(INIT_SIZE);
    private static MyHandler handler = new MyHandler();


    private static void putData() {
        for (int i= 0;i<20;i++) {
            try {
                blockingDeque.put(String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (blockingDeque.size() >= 10) {
                try {
                    getAndDealData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("putData = " + blockingDeque.size());
    }

    private static void getAndDealData() throws InterruptedException {
        String value = blockingDeque.take();
        MyRunnable runnable = new MyRunnable();
        runnable.setData(value);
        new Thread(runnable).start();
    }

    public static class MyRunnable implements Runnable {

        private String i;

        @Override
        public void run() {
            // 模拟耗时操作
            try {
                System.out.println("MyRunnable thradName = " + Thread.currentThread().getName() + "--i = " + i);
                Thread.sleep(1000);
                Message message = handler.obtainMessage();
                message.what = 0;
                message.arg1 = Integer.parseInt(i);
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void setData(String string) {
            i = string;
        }
    }

    public static class MyHandler extends Handler {
        public MyHandler(Looper mainLooper) {
            super(mainLooper);
        }

        public MyHandler() {
            super();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int args = msg.arg1;
            System.out.println("thradName = " + Thread.currentThread().getName());
            System.out.println("string value  = " + args);
        }
    }
}
