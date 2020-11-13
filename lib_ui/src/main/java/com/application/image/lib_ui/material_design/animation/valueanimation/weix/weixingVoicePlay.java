package com.application.image.lib_ui.material_design.animation.valueanimation.weix;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_ui.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuhongwen
 * on 2020/10/31
 */
public class weixingVoicePlay extends AppCompatActivity {
    private Timer mTimer;
    private AudioAnimationHandler audioAnimationHandler;
    private TimerTask mTimerTask;
    private int index = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_play_activity);
        ImageView voicePlay = findViewById(R.id.voice_play);
        playAudioAnimation(voicePlay);
    }


    /**
     * 播放语音图标动画
     */
    private void playAudioAnimation(final ImageView imageView) {
        //定时器检查播放状态
        stopTimer();
        mTimer = new Timer();
        //将要关闭的语音图片归位
        if (audioAnimationHandler != null) {
            Message msg = new Message();
            msg.what = 3;
            audioAnimationHandler.sendMessage(msg);
        }

        audioAnimationHandler = new AudioAnimationHandler(imageView);
        mTimerTask = new TimerTask() {
            public boolean hasPlayed = false;

            @Override
            public void run() {
                if (/*mMediaPlayer.isPlaying()*/ true) {
                    hasPlayed = true;
                    index = (index + 1) % 3;
                    Message msg = new Message();
                    msg.what = index;
                    audioAnimationHandler.sendMessage(msg);
                } else {
                    //当播放完时
                    Message msg = new Message();
                    msg.what = 3;
                    audioAnimationHandler.sendMessage(msg);
                    //播放完毕时需要关闭Timer等
                    if (hasPlayed) {
                        stopTimer();
                    }
                }
            }
        };
        //调用频率为500毫秒一次
        mTimer.schedule(mTimerTask, 0, 500);
    }


    class AudioAnimationHandler extends Handler {
        ImageView imageView;
        //判断是左对话框还是右对话框
        boolean isleft;

        public AudioAnimationHandler(ImageView imageView) {
            this.imageView = imageView;
            //判断是左对话框还是右对话框 我这里是在前面设置ScaleType来表示的
            isleft = imageView.getScaleType() == ImageView.ScaleType.FIT_START ? true : false;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //根据msg.what来替换图片，达到动画效果
            switch (msg.what) {
                case 0:
                    imageView.setImageResource(isleft ? R.drawable.chatfrom_voice_playing_f1 : R.drawable.chatfrom_voice_playing_f1);
                    break;
                case 1:
                    imageView.setImageResource(isleft ? R.drawable.chatfrom_voice_playing_f1 : R.drawable.chatfrom_voice_playing_f1);
                    break;
                case 2:
                    imageView.setImageResource(isleft ? R.drawable.chatfrom_voice_playing_f1 : R.drawable.chatfrom_voice_playing_f1);
                    break;
                default:
                    imageView.setImageResource(isleft ? R.drawable.chatfrom_voice_playing_f1 : R.drawable.chatfrom_voice_playing_f1);
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
