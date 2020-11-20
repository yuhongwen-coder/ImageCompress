package com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/10/14$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class TranslationMsgBean implements MultiItemEntity {
    public static final int MINE = 0;
    public static final int TOURIST = 1;
    private String orginText;
    private String targetText;
    private long time;
    private int itemType;
    private SupportLanguageBean lastSyntheticBean;

    public SupportLanguageBean getLastSyntheticBean() {
        return lastSyntheticBean;
    }

    public void setLastSyntheticBean(SupportLanguageBean lastSyntheticBean) {
        this.lastSyntheticBean = lastSyntheticBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getOrginText() {
        return orginText;
    }

    public void setOrginText(String orginText) {
        this.orginText = orginText;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
