package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section;

import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section.provider.RootFooterNodeProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section.provider.RootNodeProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section.provider.SecondNodeProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.ItemNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.RootFooterNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.RootNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseNodeAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NodeSectionAdapter extends BaseNodeAdapter {

    public NodeSectionAdapter() {
        super();
        addFullSpanNodeProvider(new RootNodeProvider());
        addNodeProvider(new SecondNodeProvider());
        addFooterNodeProvider(new RootFooterNodeProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof RootNode) {
            return 0;
        } else if (node instanceof ItemNode) {
            return 1;
        } else if (node instanceof RootFooterNode) {
            return 2;
        }
        return -1;
    }
}
