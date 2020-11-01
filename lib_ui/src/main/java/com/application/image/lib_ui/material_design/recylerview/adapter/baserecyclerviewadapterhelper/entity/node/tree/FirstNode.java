package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.tree;



import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseExpandNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FirstNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;

    public FirstNode(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;

        setExpanded(false);
    }

    public String getTitle() {
        return title;
    }


    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
