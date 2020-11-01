package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.loadmore

import android.view.View
import android.view.ViewGroup
import com.application.image.lib_ui.R
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.util.getItemView
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder


class SimpleLoadMoreView : BaseLoadMoreView() {

    override fun getRootView(parent: ViewGroup): View =
            parent.getItemView(R.layout.brvah_quick_view_load_more)

    override fun getLoadingView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_loading_view)

    override fun getLoadComplete(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_complete_view)

    override fun getLoadEndView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_end_view)

    override fun getLoadFailView(holder: BaseViewHolder): View =
            holder.getView(R.id.load_more_load_fail_view)
}