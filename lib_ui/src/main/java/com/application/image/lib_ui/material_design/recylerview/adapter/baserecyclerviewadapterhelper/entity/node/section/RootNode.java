package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section;


import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseExpandNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.NodeFooterImp;

import org.jetbrains.annotations.Nullable;
import java.util.List;

public class RootNode extends BaseExpandNode implements NodeFooterImp {

    private List<BaseNode> childNode;
    private String title;

    public RootNode(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /**
     * {@link BaseNode}
     * 重写此方法，获取子节点。如果没有子节点，返回 null 或者 空数组
     * @return child nodes
     */
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    /**
     * {@link NodeFooterImp}
     * （可选实现）
     * 重写此方法，获取脚部节点
     * @return
     */
    @Nullable
    @Override
    public BaseNode getFooterNode() {
        return new RootFooterNode("显示更多...");
    }
}
