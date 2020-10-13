package com.application.image.lib_ui.material_design.recylerview;

/**
 * Created by yuhongwen
 * on 2020/10/12
 */
public abstract class BaseFaceListTest {
    private String faceId;
    //时间 时间戳
    private String time;
    //黑、白名单   1是白名单，2是黑名单
    private String type;
    //图片路径
    private String imagePath;
    //人脸姓名
    private String name;
    //性别
    private int sex;
    //desc
    private String desc;
    //上传到 TX2 状态  0:未上传  1:已上传
    private int uploadState;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getUploadState() {
        return uploadState;
    }

    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }
}
