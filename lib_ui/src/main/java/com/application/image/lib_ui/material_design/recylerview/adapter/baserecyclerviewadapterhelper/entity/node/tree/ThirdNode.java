package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.tree;



import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThirdNode extends BaseNode {
    private String title;

    public ThirdNode(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
