package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section.provider;

import android.view.View;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.RootFooterNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.provider.BaseNodeProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootFooterNodeProvider extends BaseNodeProvider {

    public RootFooterNodeProvider() {
        addChildClickViewIds(R.id.footerTv);
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.node_footer;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @Nullable BaseNode data) {
        RootFooterNode entity = (RootFooterNode) data;
        helper.setText(R.id.footerTv, entity.getTitle());
    }

    @Override
    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        if (view.getId() == R.id.footerTv) {
            Tips.show("Footer Node Click : " + position);
        }
    }
}
