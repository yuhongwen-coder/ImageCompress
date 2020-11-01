package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.tree.provider;

import android.view.View;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.tree.SecondNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.provider.BaseNodeProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;

public class SecondProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @NotNull BaseNode data) {
        SecondNode entity = (SecondNode) data;
        helper.setText(R.id.title, entity.getTitle());

        if (entity.isExpanded()) {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_b);
        } else {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_r);
        }
    }

    @Override
    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;
        if (entity.isExpanded()) {
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
        }
    }
}
