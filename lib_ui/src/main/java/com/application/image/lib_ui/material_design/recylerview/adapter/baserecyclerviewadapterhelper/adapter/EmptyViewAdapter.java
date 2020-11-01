package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Status;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;


import org.jetbrains.annotations.NotNull;

public class EmptyViewAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {

    public EmptyViewAdapter() {
        super(R.layout.layout_animation);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull Status item) {
        switch (helper.getLayoutPosition() % 3) {
            case 0:
                helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.mipmap.animation_img3);
                break;
            default:
                break;
        }
        helper.setText(R.id.tweetName, "Hoteis in Rio de Janeiro");
        helper.setText(R.id.tweetText, "O ever youthful,O ever weeping");
    }
}
