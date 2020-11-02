package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Movie;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.module.UpFetchModule;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;

/**
 * @author: limuyang
 * @date: 2019-12-06
 * @Description:
 */
public class UpFetchAdapter extends BaseQuickAdapter<Movie, BaseViewHolder> implements UpFetchModule {

    public UpFetchAdapter() {
        super(R.layout.item_header_and_footer);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull Movie item) {
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
