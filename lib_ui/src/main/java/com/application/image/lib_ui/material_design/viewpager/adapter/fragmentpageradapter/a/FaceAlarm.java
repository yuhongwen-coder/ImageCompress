package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * author: glc.
 * Date: 2019/12/20 9:55
 * Description:
 */
@Entity(tableName = "face_alarm_tb")
public class FaceAlarm {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    //机器人唯一编号
    private String sn;
    private String passId;
    @ColumnInfo(name = "alarm_name")
    private String alarmName;
    @ColumnInfo(name = "alarm_sex")
    private String alarmSex;
    @ColumnInfo(name = "alarm_age")
    private String alarmAge;
    @ColumnInfo(name = "alarm_cardId")
    private String alarmCardId;
    @ColumnInfo(name = "alarm_level")
    private String alarmLevel;
    @ColumnInfo(name = "alarm_pass_time")
    private String alarmPassTime;
    @ColumnInfo(name = "alarm_custom_code")
    private String alarmCustomsCode;
    @ColumnInfo(name = "alarm_location")
    private String alarmLocation;
    @ColumnInfo(name = "alarm_type_code")
    private String alarmTypeCode;
    @ColumnInfo(name = "camer_name")
    private String cameraName;
    private String score;
    @ColumnInfo(name = "ie_detail_id")
    private String ieDetailId;
    @ColumnInfo(name = "ie_flag")
    private String ieFlag;
    @ColumnInfo(name = "times_tody")
    private String timesToday;
    private String handingWay;
    @ColumnInfo(name = "capture_photo_url")
    private String capturePhotoUrl;
    @ColumnInfo(name = "card_phone_url")
    private String cardPhotoUrl;
    @ColumnInfo(name = "execute_control")
    private String executeControl;
    private String times15Day;
    private String userID;
    private Long time;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmSex() {
        return alarmSex;
    }

    public void setAlarmSex(String alarmSex) {
        this.alarmSex = alarmSex;
    }

    public String getAlarmAge() {
        return alarmAge;
    }

    public void setAlarmAge(String alarmAge) {
        this.alarmAge = alarmAge;
    }

    public String getAlarmCardId() {
        return alarmCardId;
    }

    public void setAlarmCardId(String alarmCardId) {
        this.alarmCardId = alarmCardId;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmPassTime() {
        return alarmPassTime;
    }

    public void setAlarmPassTime(String alarmPassTime) {
        this.alarmPassTime = alarmPassTime;
    }

    public String getAlarmCustomsCode() {
        return alarmCustomsCode;
    }

    public void setAlarmCustomsCode(String alarmCustomsCode) {
        this.alarmCustomsCode = alarmCustomsCode;
    }

    public String getAlarmLocation() {
        return alarmLocation;
    }

    public void setAlarmLocation(String alarmLocation) {
        this.alarmLocation = alarmLocation;
    }

    public String getAlarmTypeCode() {
        return alarmTypeCode;
    }

    public void setAlarmTypeCode(String alarmTypeCode) {
        this.alarmTypeCode = alarmTypeCode;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIeDetailId() {
        return ieDetailId;
    }

    public void setIeDetailId(String ieDetailId) {
        this.ieDetailId = ieDetailId;
    }

    public String getIeFlag() {
        return ieFlag;
    }

    public void setIeFlag(String ieFlag) {
        this.ieFlag = ieFlag;
    }

    public String getTimesToday() {
        return timesToday;
    }

    public void setTimesToday(String timesToday) {
        this.timesToday = timesToday;
    }

    public String getHandingWay() {
        return handingWay;
    }

    public void setHandingWay(String handingWay) {
        this.handingWay = handingWay;
    }

    public String getCapturePhotoUrl() {
        return capturePhotoUrl;
    }

    public void setCapturePhotoUrl(String capturePhotoUrl) {
        this.capturePhotoUrl = capturePhotoUrl;
    }

    public String getCardPhotoUrl() {
        return cardPhotoUrl;
    }

    public void setCardPhotoUrl(String cardPhotoUrl) {
        this.cardPhotoUrl = cardPhotoUrl;
    }

    public String getExecuteControl() {
        return executeControl;
    }

    public void setExecuteControl(String executeControl) {
        this.executeControl = executeControl;
    }

    public String getTimes15Day() {
        return times15Day;
    }

    public void setTimes15Day(String times15Day) {
        this.times15Day = times15Day;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
