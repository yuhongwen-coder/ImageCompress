package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.animation;

import android.animation.Animator
import android.view.View

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
interface BaseAnimation {
    fun animators(view: View): Array<Animator>
}