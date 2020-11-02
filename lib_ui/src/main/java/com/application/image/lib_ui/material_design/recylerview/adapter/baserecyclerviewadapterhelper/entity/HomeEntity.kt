
package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.SectionEntity


/**
 * @author: limuyang
 * @date: 2019-12-06
 * @Description:
 */
data class HomeEntity(
        val name: String = "",
        val activity: Class<*>? = null,
        val imageResource: Int = 0,
        val headerTitle: String = ""
) : SectionEntity {
    override val isHeader: Boolean
        get() = headerTitle.isNotBlank()
}