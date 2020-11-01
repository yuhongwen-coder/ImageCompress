
package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter
import com.application.image.lib_ui.R
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.HomeEntity
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseSectionQuickAdapter
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseViewHolder



/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
class HomeAdapter(data: MutableList<HomeEntity>) :
        BaseSectionQuickAdapter<HomeEntity, BaseViewHolder>(R.layout.def_section_head, R.layout.home_item_view, data) {

    override fun convert(helper: BaseViewHolder, item: HomeEntity) {

        helper.setText(R.id.text, item.name)
        helper.setImageResource(R.id.icon, item.imageResource)
    }

    override fun convertHeader(helper: BaseViewHolder, item: HomeEntity) {
        helper.setGone(R.id.more, true)
        helper.setText(R.id.header, item.headerTitle)
    }
}