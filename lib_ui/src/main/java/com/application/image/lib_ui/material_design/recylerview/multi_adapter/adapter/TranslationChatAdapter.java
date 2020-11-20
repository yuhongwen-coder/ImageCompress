package com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.TranslationMsgBean;
import com.application.image.lib_ui.utils.TimeUtil;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/10/14$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class TranslationChatAdapter extends BaseMultiItemQuickAdapter<TranslationMsgBean, BaseViewHolder> {

    private playAudioListener clickListener;
    private VoiceViewInterface voiceViewHolderListener;
    private List<VoiceViewHolder> voiceViewHolderList = new ArrayList<>();

    public TranslationChatAdapter(List<TranslationMsgBean> data) {
        super(data);
        addItemType(TranslationMsgBean.MINE, R.layout.item_mine_translate_chat);
        addItemType(TranslationMsgBean.TOURIST, R.layout.item_tourist_tranlate_chat);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, final TranslationMsgBean translationMsgBean) {
        helper.setText(R.id.origin_content, translationMsgBean.getOrginText());
        helper.setText(R.id.target_content, translationMsgBean.getTargetText());
        if (getItemPosition(translationMsgBean)!=0){
           if (translationMsgBean.getTime()- getData().get(getItemPosition(translationMsgBean)-1).getTime()>2000){
               helper.setGone(R.id.tv_time,false);
           }else{
               helper.setGone(R.id.tv_time,true);
           }
        }else{
            helper.setGone(R.id.tv_time,true);
        }
        helper.getView(R.id.tv_time).setVisibility(View.GONE);
        helper.setText(R.id.tv_time, TimeUtil.getTimeHourMinutes(translationMsgBean.getTime()));
        final ImageView normalVoicePlay = helper.getView(R.id.play_translate);
        final ImageView voicePlaying = helper.getView(R.id.playing_voice_translate);
        final RelativeLayout voicePlayingRl = helper.getView(R.id.playing_rl);
        helper.getView(R.id.voice_play_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.clickListener(TranslationChatAdapter.this.getItemPosition(translationMsgBean),
                            normalVoicePlay, voicePlayingRl,
                            voicePlaying);
                }
            }
        });

        // holder 最后一个View
        if (voiceViewHolderListener != null && getItemPosition(translationMsgBean) == getDefItemCount()-1) {
            VoiceViewHolder holder = new VoiceViewHolder();
            holder.setNormalVoicePlay(normalVoicePlay);
            holder.setVoicePlaying(voicePlaying);
            holder.setVoicePlayingRl(voicePlayingRl);
            voiceViewHolderList.add(holder);
            voiceViewHolderListener.voiceHolderView(voiceViewHolderList,getDefItemCount()-1);
        }
    }

    public void setPlayAudioListener(playAudioListener listener) {
        clickListener = listener;

    }

    public void setVoiceViewHolderListener(VoiceViewInterface holder) {
        voiceViewHolderListener = holder;
    }

    public interface playAudioListener{
        void clickListener(int position, ImageView imageView,RelativeLayout voicePlayingParent,ImageView voicePlaying);
    }

    /**
     * 增加在合成语音之后，可以直接播放语音动效
     */
    public interface VoiceViewInterface {
        void voiceHolderView(List<VoiceViewHolder> voiceViewHolder,int lastPosition);
    }

    public class VoiceViewHolder {
        private ImageView normalVoicePlay;
        private RelativeLayout voicePlayingRl;
        private ImageView voicePlaying;

        public ImageView getNormalVoicePlay() {
            return normalVoicePlay;
        }

        public void setNormalVoicePlay(ImageView normalVoicePlay) {
            this.normalVoicePlay = normalVoicePlay;
        }

        public RelativeLayout getVoicePlayingRl() {
            return voicePlayingRl;
        }

        public void setVoicePlayingRl(RelativeLayout voicePlayingRl) {
            this.voicePlayingRl = voicePlayingRl;
        }

        public ImageView getVoicePlaying() {
            return voicePlaying;
        }

        public void setVoicePlaying(ImageView voicePlaying) {
            this.voicePlaying = voicePlaying;
        }
    }
}
