package com.application.image.lib_ui.material_design.recylerview.multi_adapter.spread;

import android.util.Log;
import android.view.View;

import com.application.image.lib_ui.utils.AssetsSourceUtils;


/**
 * name: wy
 * date: 2020/9/1
 * desc:
 */
public class SpreadViewManager {

    private static volatile SpreadViewManager instance;

    private SpreadView spreadView;

    private boolean isIdentify; //是否正在识别 true：正在识别
    private boolean isSynthetic; //是否语音合成 true：正在合成播报

    public static SpreadViewManager get() {
        if (instance == null) {
            synchronized (SpreadViewManager.class) {
                instance = new SpreadViewManager();
            }
        }
        return instance;
    }

    public SpreadViewManager(){
        spreadView = new SpreadView(AssetsSourceUtils.getApplication());
        spreadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpreadViewManager.this.startVoice();
            }
        });
    }

    public boolean isIdentify() {
        return isIdentify;
    }

    public boolean isSynthetic() {
        return isSynthetic;
    }

    public SpreadView getSpreadView() {
        return spreadView;
    }

    /**
     * 开始识别
     */
    public void startVoice(){
        if(isIdentify){
            stopVoice();
            return;
        }
        if(isSynthetic){
            stopSynthetic();
            return;
        }
    }

    /**
     * 结束识别
     */
    public void stopVoice(){
        isIdentify = false;
        spreadView.stopAnimator();
    }

    /**
     * 结束合成
     */
    public void stopSynthetic(){
        isSynthetic = false;
        spreadView.stopAnimator();
    }


    private void setTextMic(String mic){
        spreadView.setTextMic(mic);
    }


    /**
     *  语音合成
     */
    public void synthetic(String content){
        if(isSynthetic){
            stopSynthetic();
            return;
        }
    }

}