package com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean;

import com.google.gson.annotations.SerializedName;


/**
 * language : ar ------------ 翻译用
 * name : 阿拉伯语
 * en : Arabic
 * asr : true
 * tts : false
 * voice_name : ar-EG ------- 识别与合成用
 * voice_server : Microsoft Server Speech Text to Speech Voice (ar-EG,Hoda)
 */
public class SupportLanguageBean extends BaseIndexPinyinBean  {

    // 英文简写
    @SerializedName("language")
    private String enName;

    // 中文全写
    @SerializedName("name")
    private String zhFullName;

    // 英文全写.
    @SerializedName("en")
    private String enFullName;

    // 是否支持听写
    @SerializedName("asr")
    private boolean isSupportAsr;

    // 是否支持播放
    @SerializedName("tts")
    private boolean isSupportTts;

    // 语言代码
    private String voice_name;

    //新增三字码
    @SerializedName("icao_code")
    private String icao_code;

    private String language_number;

    private String name2;

    @SerializedName("url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 微软语音合成服务器节点
    private String voice_server;
    private String voice_server2;
    private String voice_server3;
    private String voice_server4;
    private String voice_server5;
    private boolean isCheck;
    private boolean needToShow=true;

    public boolean isNeedToShow() {
        return needToShow;
    }

    public void setNeedToShow(boolean needToShow) {
        this.needToShow = needToShow;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getLanguage_number() {
        return language_number;
    }

    public void setLanguage_number(String language_number) {
        this.language_number = language_number;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getIcao_code() {
        return icao_code;
    }

    public void setIcao_code(String icao_code) {
        this.icao_code = icao_code;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhFullName() {
        return zhFullName;
    }

    public void setZhFullName(String zhFullName) {
        this.zhFullName = zhFullName;
    }

    public String getEnFullName() {
        return enFullName;
    }

    public void setEnFullName(String enFullName) {
        this.enFullName = enFullName;
    }

    public boolean isSupportAsr() {
        return isSupportAsr;
    }

    public void setSupportAsr(boolean supportAsr) {
        isSupportAsr = supportAsr;
    }

    public boolean isSupportTts() {
        return isSupportTts;
    }

    public void setSupportTts(boolean supportTts) {
        isSupportTts = supportTts;
    }

    public String getVoice_name() {
        return voice_name;
    }

    public void setVoice_name(String voice_name) {
        this.voice_name = voice_name;
    }

    public String getVoice_server() {
        return voice_server;
    }

    public void setVoice_server(String voice_server) {
        this.voice_server = voice_server;
    }

    public String getVoice_server2() {
        return voice_server2;
    }

    public void setVoice_server2(String voice_server2) {
        this.voice_server2 = voice_server2;
    }

    public String getVoice_server3() {
        return voice_server3;
    }

    public void setVoice_server3(String voice_server3) {
        this.voice_server3 = voice_server3;
    }

    public String getVoice_server4() {
        return voice_server4;
    }

    public void setVoice_server4(String voice_server4) {
        this.voice_server4 = voice_server4;
    }

    public String getVoice_server5() {
        return voice_server5;
    }

    public void setVoice_server5(String voice_server5) {
        this.voice_server5 = voice_server5;
    }


    @Override
    public String getTarget() {
        return zhFullName;
    }
}
