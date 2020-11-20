package com.application.image.lib_ui.material_design.recylerview.multi_adapter.view;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;


import com.application.image.lib_ui.R;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuhongwen
 * on 2020/11/02
 */
public class RobotVoicePlayAnimation {
    private Timer mTimer;
    private AudioAnimationHandler audioAnimationHandler;
    private TimerTask mTimerTask;
    private int index = 1;
    private static volatile RobotVoicePlayAnimation instance;
    private boolean hasPlayed;

    public static RobotVoicePlayAnimation getInstance() {
        if (instance == null) {
            synchronized (RobotVoicePlayAnimation.class) {
                instance = new RobotVoicePlayAnimation();
            }
        }
        return instance;
    }

    public void stopPalyAudio() {
        Log.e("wangyin","stopPalyAudio");
        Message msg = new Message();
        msg.what = 3;
        if (audioAnimationHandler != null) {
            audioAnimationHandler.sendMessage(msg);
            //播放完毕时需要关闭Timer等
        }
        if (hasPlayed) {
            stopTimer();
        }
    }

    /**
     * 播放语音图标动画
     */
    public void playAudioAnimation(final ImageView imageView) {
        //定时器检查播放状态
        stopTimer();
        mTimer = new Timer();
        if (audioAnimationHandler != null) {
            Message msg = new Message();
            msg.what = 3;
            audioAnimationHandler.sendMessage(msg);
        }
        audioAnimationHandler = new AudioAnimationHandler(imageView);
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                hasPlayed = true;
                index = (index + 1) % 3;
                Message msg = new Message();
                msg.what = index;
                audioAnimationHandler.sendMessage(msg);
            }
        };
        //调用频率为300毫秒一次
        mTimer.schedule(mTimerTask, 0, 300);
    }

    protected static class AudioAnimationHandler extends Handler {
        ImageView imageView;

        public AudioAnimationHandler(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            //根据msg.what来替换图片，达到动画效果
            if (imageView == null) {
                return;
            }
            switch (msg.what) {
                case 0:
                    imageView.setImageResource(R.drawable.robot_voice_playing_f1);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.robot_voice_playing_f2);
                    break;
                default:
                    imageView.setImageResource(R.drawable.robot_voice_playing_f3);
                    break;
            }
        }
    }

    /**
     * 停止
     */
    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
