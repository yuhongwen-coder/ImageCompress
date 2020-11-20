package com.application.image.lib_ui.material_design.recylerview.multi_adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.TranslationChatAdapter;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.SupportLanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.TranslationMsgBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.dialog.SelectLanguageDialog;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.manager.PreferencesHelper;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RippleView;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RobotVoicePlayAnimation;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RoundedImageView;
import com.application.image.lib_ui.utils.AssetsSourceUtils;
import com.google.gson.Gson;






import java.util.ArrayList;
import java.util.List;

import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_AR;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_EN;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_JSON;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LAST_ORIGIN_LANGUAGE;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LAST_TARGET_LANGUAGE;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.TARGET_CONTENT;


public class ChatMessageActivity extends AppCompatActivity implements View.OnClickListener, TranslationChatAdapter.playAudioListener, SelectLanguageDialog.DialogDismissEvent, TranslationChatAdapter.VoiceViewInterface {

    private static final int SYNTHETIC_VOICE = 1;
    private static final int IDENTITY_VOICE = 2;
    private RecyclerView rvMsg;
    private RoundedImageView ivOriginImageView;
    private RoundedImageView ivTargetImageView;
    private List<TranslationMsgBean> translationMsgBeans;
    private RippleView originRipple;
    private RippleView targetRipple;
    private volatile boolean isIdentify; //是否正在识别 true：正在识别
    private int originId;

    private Gson gson = new Gson();
    private String languages;

    private long lastClickTime = 0;
    private HandlerThread handlerThread;
    private Handler workHandler;
    private TextView originLangagueText;
    private TextView targetLangagueText;
    private SelectLanguageDialog selectLanguageDialog;
    private SupportLanguageBean originBean;
    private SupportLanguageBean targetBean;
    private SupportLanguageBean lastIdentifyBean = new SupportLanguageBean();
    private SupportLanguageBean lastSyntheticBean = new SupportLanguageBean();
    private ImageView normalVoicePlay;
    private RelativeLayout voicePlayingRl;
    private ImageView voicePlaying;
    private volatile boolean isSynthetic;
    private boolean isVoiceAnimation;
    private int lastVoicePlayingPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        initVeiw();
        initData();
    }

    private void initData() {
        PreferencesHelper.init(this);
        languages = AssetsSourceUtils.getJsonString(LANGUAGE_JSON);
        configHandlerThread();
        configDialogFragment();
        translationMsgBeans = new ArrayList<>();
        String originLanguageJson = PreferencesHelper.getData(LAST_ORIGIN_LANGUAGE);
        String targetLanguageJson = PreferencesHelper.getData(LAST_TARGET_LANGUAGE);
        // sp 有bug  返回的是  “null ”
        configCurrentLanguage();
    }

    @SuppressLint("SetTextI18n")
    private void configCurrentLanguage() {
        String originLanguageJson = PreferencesHelper.getData(LAST_ORIGIN_LANGUAGE);
        String targetLanguageJson = PreferencesHelper.getData(LAST_TARGET_LANGUAGE);
        originBean = gson.fromJson(originLanguageJson, SupportLanguageBean.class);
        targetBean = gson.fromJson(targetLanguageJson, SupportLanguageBean.class);
        originLangagueText.setText(originBean.getZhFullName()+"("+originBean.getName2()+")");
        configCountryIcon(ivOriginImageView,originBean);
        targetLangagueText.setText(targetBean.getZhFullName() + "("+ targetBean.getName2() + ")");
        configCountryIcon(ivTargetImageView,targetBean);

    }

    private void configCountryIcon(ImageView imageView, SupportLanguageBean bean) {
        String imageUrl = bean.getImageUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            if (TextUtils.equals(bean.getEnName(), LANGUAGE_EN)) {
                imageView.setImageResource(getResources().getIdentifier("common_en", "mipmap", getPackageName()));
            } else if (TextUtils.equals(bean.getEnName(),LANGUAGE_AR)) {
                imageView.setImageResource(getResources().getIdentifier("common_ar", "mipmap", getPackageName()));
            }
            return;
        }
        imageView.setImageResource(getResources().getIdentifier(imageUrl, "mipmap", getPackageName()));
    }



    private void configDialogFragment() {
        selectLanguageDialog = new SelectLanguageDialog();
        selectLanguageDialog.setDialogListener(this);
    }

    /**
     *  HandlerThread 在页面销毁的时候 线程停止，也可以减少启动线程的开销
     */
    private void configHandlerThread() {
        handlerThread = new HandlerThread("voiceThread");
        handlerThread.start();
        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == SYNTHETIC_VOICE) {
                    String syntheticContent = "";
                    Bundle bundle = msg.getData();
                    if (bundle.get(TARGET_CONTENT) instanceof String) {
                        syntheticContent= (String) bundle.get(TARGET_CONTENT);
                    }
                    syntheticVoice(syntheticContent);
                } else if (msg.what == IDENTITY_VOICE) {
//                    VoiceUtils.setIdentifyCountry(lastIdentifyBean.getIcao_code());
                    Log.i("wangyin", "识别开始语言 code码 = " + lastIdentifyBean.getLanguage_number());
                }
            }
        };
    }

    private void syntheticVoice(String targetContent) {
        isSynthetic = true;
//        Log.i("wangyin", "合成函数开始三字码 = " + lastSyntheticBean.getIcao_code() + "---targetContent = " + targetContent);
        Log.i("wangyin", "合成函数开始code码 = " + lastSyntheticBean.getLanguage_number() + "---targetContent = " + targetContent);
        Log.i("wangyin", "合成函数开始 是否来了一条语音合成 = " + isIdentify);
        if (isIdentify) {
            // 合成之前，发现来了一条语音，就终止合成(直接返回)
            return;
        }
    }

    private void stopVoiceAnimation() {
        if (normalVoicePlay != null) {
            normalVoicePlay.setVisibility(View.VISIBLE);
        }
        if (voicePlayingRl != null) {
            voicePlayingRl.setVisibility(View.GONE);
        }
        isVoiceAnimation = false;
    }

    private void configLanguage() {
        String originLanguageJson = PreferencesHelper.getData(LAST_ORIGIN_LANGUAGE);
        String targetLanguageJson = PreferencesHelper.getData(LAST_TARGET_LANGUAGE);
        if (!TextUtils.isEmpty(originLanguageJson)) {
            originBean = gson.fromJson(originLanguageJson, SupportLanguageBean.class);
        }
        if (!TextUtils.isEmpty(targetLanguageJson)) {
            targetBean = gson.fromJson(targetLanguageJson, SupportLanguageBean.class);
        }
    }

    private void initVeiw() {
        rvMsg = findViewById(R.id.rv_msg);
        findViewById(R.id.iv_iv_up_arrow_parent).setOnClickListener(this);
        findViewById(R.id.ripple_origin_view_fr).setOnClickListener(this);
        ivOriginImageView = findViewById(R.id.iv_origin_language);
        originRipple = findViewById(R.id.ripple_origin_view);
        ivOriginImageView.setOnClickListener(this);
        originRipple.setOnClickListener(this);
        findViewById(R.id.ripple_targert_view_fr).setOnClickListener(this);
        ivTargetImageView = findViewById(R.id.iv_target_language);
        targetRipple = findViewById(R.id.ripple_targert_view);
        originLangagueText = findViewById(R.id.tv_origin_language);
        targetLangagueText = findViewById(R.id.tv_target_language);
        targetRipple = findViewById(R.id.ripple_targert_view);
        targetRipple.setOnClickListener(this);
        ivTargetImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        long now = System.currentTimeMillis();
        Log.e("wangyin", "user click now = " + now);
        Log.e("wangyin", "user click lastClickTime = " + lastClickTime);
        if (now - lastClickTime < 50) {
            lastClickTime = now;
            Log.e("wangyin", "user click too quick");
            return;
        }
        lastClickTime = now;
        int id = v.getId();
        if (id == R.id.iv_iv_up_arrow_parent) {
            FragmentManager fm = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            selectLanguageDialog.setArguments(bundle);
            // bug fix Fragment already added: SelectLanguageDialog
            if (!selectLanguageDialog.isAdded()) {
                selectLanguageDialog.show(fm, "");
            }
            stopVoiceAnimation();
            stopVoice(originId);
        } else if (id == R.id.iv_origin_language) {
            originId = R.id.iv_origin_language;
            startVoice(originId);
        } else if (id == R.id.iv_target_language) {
            originId = R.id.iv_target_language;
            startVoice(originId);
        } else if (id == R.id.ripple_origin_view) {
            stopVoice(R.id.ripple_origin_view); // 源语言自己取消
            originId = R.id.ripple_origin_view;
        } else if (id == R.id.ripple_targert_view) {
            stopVoice(R.id.ripple_targert_view); // 目标语言自己取消
            originId = R.id.ripple_targert_view;
        }
    }

    /**
     * 启动动画
     * @param resId 启动动画id
     */
    private void startRipple(int resId) {
        if (resId == R.id.iv_origin_language) {
            Log.e("wangyin", "startRipple origin enter ");
            if (targetRipple.getVisibility() == View.VISIBLE) {
                stopVoice(R.id.ripple_targert_view); // 目标语言 源语言互斥取消
            }
            originRipple.startRipple();
            ivOriginImageView.setVisibility(View.GONE);
            originLangagueText.setVisibility(View.GONE);
        } else if (resId == R.id.iv_target_language) {
            Log.e("wangyin", "startRipple target enter ");
            if (originRipple.getVisibility() == View.VISIBLE) {
                stopVoice(R.id.ripple_origin_view); // 目标语言 源语言互斥取消
            }
            targetRipple.startRipple();
            ivTargetImageView.setVisibility(View.GONE);
            targetLangagueText.setVisibility(View.GONE);
        }
    }

    private void startVoice(int originId) {
        Log.e("wangyin", "startVoice enter");
        // 1：开启动画,结束播报
        startRipple(originId);
        stopVoiceAnimation();
        // 2：配置识别合成语音类型
        configLanguage();
        if (originId == R.id.iv_origin_language) {
            lastIdentifyBean = originBean;
            lastSyntheticBean = targetBean;
        } else if (originId == R.id.iv_target_language) {
            lastIdentifyBean = targetBean;
            lastSyntheticBean = originBean;
        } else {
            lastIdentifyBean = originBean;
            lastSyntheticBean = targetBean;
        }
        // 3： 如果上一条语音还在识别或者合成，就销毁识别合成
        Log.e("wangyin", "startVoice isIdentify = " + isIdentify + "---isSynthetic = " + isSynthetic);
        // 4： 本条语言开始合成
        isIdentify = true;
        Message message = Message.obtain();
        message.what = IDENTITY_VOICE;
        workHandler.sendMessage(message);
    }

    private void stopVoice(int resId) {
        if (resId == R.id.ripple_origin_view || resId == R.id.iv_origin_language) {
            Log.e("wangyin", "stopRipple ripple_origin_view ");
            originRipple.stopRipple();
            ivOriginImageView.setVisibility(View.VISIBLE);
            originLangagueText.setVisibility(View.VISIBLE);
        } else if (resId == R.id.ripple_targert_view || resId == R.id.iv_target_language) {
            Log.e("wangyin", "stopRipple ripple_targert_view ");
            targetRipple.stopRipple();
            ivTargetImageView.setVisibility(View.VISIBLE);
            targetLangagueText.setVisibility(View.VISIBLE);
        } else {
            originRipple.stopRipple();
            targetRipple.stopRipple();
            ivOriginImageView.setVisibility(View.VISIBLE);
            originLangagueText.setVisibility(View.VISIBLE);
            ivTargetImageView.setVisibility(View.VISIBLE);
            targetLangagueText.setVisibility(View.VISIBLE);
            Log.e("wangyin", "stopRipple other ");
        }
    }

    private void syntheticLanguage(String target) {
        Message message = Message.obtain();
        message.what = SYNTHETIC_VOICE;
        Bundle bundle = new Bundle();
        bundle.putString(TARGET_CONTENT,target);
        message.setData(bundle);
        workHandler.sendMessage(message);
    }

    @Override
    public void clickListener(int position, ImageView normalVoice,RelativeLayout imageViewPlayingRl,ImageView imageViewPlaying) {
        Log.i("wangyin", "点击播放语音 position = " + position);
        Log.i("wangyin", "点击播放语音，判断是否有新语音识别 = " + isIdentify+"--是否有新语音在合成 = "+ isSynthetic);
        // 用户点击优先级最高
        Log.i("wangyin", "点击播放语音，判断是否有语音在播报 = " + isVoiceAnimation);
        if (isVoiceAnimation) {
            // View.getId都是一样的，只能在虚拟一个变量
            if (lastVoicePlayingPosition == position) {
                Log.i("wangyin", "停止当前语音播报并中断合成");
                stopVoiceAnimation();
                return;
            }
            Log.i("wangyin", "停止当前语音播报并合成新语音");
            stopVoiceAnimation();
        }
        if (translationMsgBeans != null && translationMsgBeans.size() > position) {
            if (translationMsgBeans.get(position) != null) {
                normalVoicePlay = normalVoice;
                voicePlayingRl = imageViewPlayingRl;
                voicePlaying = imageViewPlaying;
                lastVoicePlayingPosition = position;
                syntheticLanguage(translationMsgBeans.get(position).getTargetText());
                autoVoiceAnimation();
            } else {
                Log.i("wangyin", "播放语音 合成内容为空");
            }
        }
    }

    private void autoVoiceAnimation() {
        if (voicePlayingRl != null) {
            voicePlayingRl.setVisibility(View.VISIBLE);
        }
        if (normalVoicePlay != null) {
            normalVoicePlay.setVisibility(View.GONE);
        }
        if (voicePlaying != null) {
            RobotVoicePlayAnimation.getInstance().playAudioAnimation(voicePlaying);
    }
        isVoiceAnimation = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onStop() {
        super.onStop();
        // 复位语言
        handlerThread.quitSafely();
    }

    @Override
    public void pushLangague() {
        configCurrentLanguage();
    }

    /**
     * 回调最后一个位置
     * @param voiceViewHolder 存储是最后一个条目
     * @param lastPosition 最后一个位置
     */
    @Override
    public void voiceHolderView(List<TranslationChatAdapter.VoiceViewHolder> voiceViewHolder, int lastPosition) {
        if (voiceViewHolder != null && voiceViewHolder.size() >0) {
            // 先暂时只需要获取最后一个条目
            TranslationChatAdapter.VoiceViewHolder holder = voiceViewHolder.get(0);
            normalVoicePlay = holder.getNormalVoicePlay();
            voicePlayingRl = holder.getVoicePlayingRl();
            voicePlaying = holder.getVoicePlaying();
            lastVoicePlayingPosition = lastPosition;
        }
    }
}