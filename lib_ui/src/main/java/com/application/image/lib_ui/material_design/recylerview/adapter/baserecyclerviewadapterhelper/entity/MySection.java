package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity;


import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.JSectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * <p>
 * java请自定义类，继承于JSectionEntity抽象类。封装一遍自己的数据类
 * <p>
 * kotlin，数据类请直接实现SectionEntity接口即可，无需封装。
 */
public class MySection extends JSectionEntity {
    private boolean isHeader;
    private Object  object;

    public MySection(boolean isHeader, Object object) {
        this.isHeader = isHeader;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

}
