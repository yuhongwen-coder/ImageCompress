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
public class LanguageBean {
    private List<InitialSupportLanguageBean> language;
    private List<SupportLanguageBean> hot_language;

    public List<InitialSupportLanguageBean> getLanguage() {
        return language;
    }

    public void setLanguage(List<InitialSupportLanguageBean> language) {
        this.language = language;
    }

    public List<SupportLanguageBean> getHot_language() {
        return hot_language;
    }

    public void setHot_language(List<SupportLanguageBean> hot_language) {
        this.hot_language = hot_language;
    }
}
