package com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean;

import java.util.List;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/9/28$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class LanguageHeaderBean extends BaseIndexPinyinBean {
    private List<SupportLanguageBean> cityList;
    //悬停ItemDecoration显示的Tag
    private String suspensionTag;

    public LanguageHeaderBean() {
    }

    public LanguageHeaderBean(List<SupportLanguageBean> cityList, String suspensionTag, String indexBarTag) {
        this.cityList = cityList;
        this.suspensionTag = suspensionTag;
        this.setBaseIndexTag(indexBarTag);
    }

    public List<SupportLanguageBean> getCityList() {
        return cityList;
    }

    public LanguageHeaderBean setCityList(List<SupportLanguageBean> cityList) {
        this.cityList = cityList;
        return this;
    }

    public LanguageHeaderBean setSuspensionTag(String suspensionTag) {
        this.suspensionTag = suspensionTag;
        return this;
    }

    @Override
    public String getTarget() {
        return null;
    }

    @Override
    public boolean isNeedToPinyin() {
        return false;
    }

    @Override
    public String getSuspensionTag() {
        return suspensionTag;
    }


}

