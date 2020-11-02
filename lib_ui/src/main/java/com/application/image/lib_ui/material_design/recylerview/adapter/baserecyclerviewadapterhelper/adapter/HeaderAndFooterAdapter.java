package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter;

import androidx.annotation.NonNull;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.data.DataServer;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Status;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public HeaderAndFooterAdapter(int dataSize) {
        super(R.layout.item_header_and_footer, DataServer.getSampleData(dataSize));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Status item) {
        switch (helper.getLayoutPosition() %
                3) {
            case 0:
                helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.iv, R.mipmap.animation_img3);
                break;
            default:
                break;
        }
    }


}
