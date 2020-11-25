package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.b;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * name: wy
 * date: 2020/8/28
 * desc: 人脸库数据
 */
@Entity
public class FACE_LIST_DATA_ {
    @Id(autoincrement = true)
    private Long id;
    //唯一ID
    @NotNull
    @Index(unique = true)
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
    @Generated(hash = 854639371)
    public FACE_LIST_DATA_(Long id, @NotNull String faceId, String time,
                           String type, String imagePath, String name, int sex, String desc,
                           int uploadState) {
        this.id = id;
        this.faceId = faceId;
        this.time = time;
        this.type = type;
        this.imagePath = imagePath;
        this.name = name;
        this.sex = sex;
        this.desc = desc;
        this.uploadState = uploadState;
    }
    @Generated(hash = 511963860)
    public FACE_LIST_DATA_() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFaceId() {
        return this.faceId;
    }
    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getUploadState() {
        return this.uploadState;
    }
    public void setUploadState(int uploadState) {
        this.uploadState = uploadState;
    }
}