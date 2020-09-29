package com.application.image.lib_thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by yuhongwen
 * on 2020/9/28
 */
public class TestHandlerDemo {
    private static final int INIT_SIZE = 10;
    private static BlockingDeque<String> blockingDeque = new LinkedBlockingDeque(INIT_SIZE);
    private static MyHandler handler = new MyHandler();

    public static void main(String[] args) {
        putData();
    }

    private static void putData() {
        for (int i= 0;i<100;i++) {
            try {
                blockingDeque.put(String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                getAndDealData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getAndDealData() throws InterruptedException {
        String value = blockingDeque.take();
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable).start();
    }

    public static class MyRunnable implements Runnable {

        private String i;

        @Override
        public void run() {
            // 模拟耗时操作
            try {
                Thread.sleep(3000);
                i = i + "0";
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
