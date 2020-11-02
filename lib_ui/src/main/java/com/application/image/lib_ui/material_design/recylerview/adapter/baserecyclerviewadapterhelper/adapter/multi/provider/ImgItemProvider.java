package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.multi.provider;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.ProviderMultiEntity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.provider.BaseItemProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;


import org.jetbrains.annotations.NotNull;


/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: Img ItemProvider
 * @date 2018/3/30  11:39
 */

public class ImgItemProvider extends BaseItemProvider<ProviderMultiEntity> {

    @Override
    public int getItemViewType() {
        return ProviderMultiEntity.IMG;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image_view;
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, @Nullable ProviderMultiEntity data) {
        if (helper.getAdapterPosition() % 2 == 0) {
            helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
        } else {
            helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
        }
    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {
        Tips.show("Click: " + position);
    }

    @Override
    public boolean onLongClick(@NotNull BaseViewHolder helper, @NotNull View view, ProviderMultiEntity data, int position) {
        Tips.show("Long Click: " + position);
        return true;
    }
}
