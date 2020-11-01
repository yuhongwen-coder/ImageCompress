package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section;



import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootFooterNode extends BaseNode {

    private String title;

    public RootFooterNode(String title) {
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
