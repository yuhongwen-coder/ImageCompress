package com.maxvision.tech.websocket;

import android.os.Handler;
import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuhongwen
 * on 2020/10/26
 */
public class WebSocketManager {
    private WebSocketClient client;
    private static final String TAG = "WebSocketManager";
    private static volatile WebSocketManager instance;
    private Timer timer;
    private int count;
    private Timer delayTimer;
    private Handler mHandler = new Handler();

    public static WebSocketManager getInstance() {
        if (instance == null) {
            synchronized (WebSocketManager.class) {
                if (instance == null) {
                    instance = new WebSocketManager();
                }
            }
        }
        return instance;
    }

    public void connectWebSocket(String url) {
        URI uri = URI.create(url);
        client = new WebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                Log.e(TAG,"initWebsocket onMessage");
                if (!message.contains("pong")) {
                    // 非心跳信息，直接将推送的消息发给页面
                }
                clearHeartTime();
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.e(TAG,"initWebsocket onOpen");
                startHeartBeat();
            }

            @Override
            public void onError(Exception ex) {
                Log.e(TAG,"initWebsocket onError");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG,"initWebsocket onClose");

            }
        };
        client.connect();
        // 方式二 心跳检测 暴力点 直接开启心跳检测
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }

    private static final long HEART_BEAT_RATE = 3 * 1000;//每隔3秒进行一次对长连接的心跳检测

    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG,"心跳执行");
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }
            } else {
                //如果client已为空，重新初始化websocket
                connectWebSocket("");
            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void clearHeartTime() {
        timer.cancel();
        count = 0;
        delayTimer.cancel();
    }

    /**
     * 方式一 心跳检测
     */
    private void startHeartBeat() {
        timer = new Timer();//创建timer对象
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 发送心跳探针
                count++;
                client.sendPing();
            }
        }, 0, 3 * 1000);//每3秒发一次心跳

        delayTimer = new Timer();
        delayTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 12s 延时已经到了.count 没有被清零
                if (count != 0) {
                    reConnectWebSocket();
                }
            }
        },12000);
    }

    private void reConnectWebSocket() {
        if (client != null) {
            client.close();
            client.reconnect();
        }
    }
}
