package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.multi.provider;

import android.view.View;

import androidx.annotation.NonNull;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.ProviderMultiEntity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.provider.BaseItemProvider;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: Text ItemProvider
 * @date 2018/3/30  11:39
 */

public class TextItemProvider extends BaseItemProvider<ProviderMultiEntity> {
    @Override
    public int getItemViewType() {
        return ProviderMultiEntity.TEXT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_view;
    }

    @Override
    public void convert(@NotNull BaseViewHolder helper, @Nullable ProviderMultiEntity data) {
        helper.setText(R.id.tv, "CymChad content : " + helper.getAdapterPosition());
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
