package com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean;

import java.util.List;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/9/29$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class InitialSupportLanguageBean extends BaseIndexPinyinBean {
    private String initial;
    private List<SupportLanguageBean> supportLanguageBeans;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public List<SupportLanguageBean> getSupportLanguageBeans() {
        return supportLanguageBeans;
    }

    public void setSupportLanguageBeans(List<SupportLanguageBean> supportLanguageBeans) {
        this.supportLanguageBeans = supportLanguageBeans;
    }
    @Override
    public String getTarget() {
        return initial;
    }

    @Override
    public boolean isShowSuspension() {
        return true;
    }
}
