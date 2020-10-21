package com.application.image.lib_ui.material_design.popupwindow;

/**
 * Created by yuhongwen
 * on 2020/10/20
 */
public class BoxModel extends BoxSize{
    private String boxIsEmpty;

    public String getBoxIsEmpty() {
        return boxIsEmpty;
    }

    public void setBoxIsEmpty(String boxIsEmpty) {
        this.boxIsEmpty = boxIsEmpty;
    }

    @Override
    public String toString() {
        return "BoxModel{" +
                "boxIsEmpty='" + boxIsEmpty + '\'' +
                '}';
    }
}
