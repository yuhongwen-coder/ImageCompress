package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener;

import androidx.annotation.Nullable;

/**
 * @author: limuyang
 * @date: 2019-12-05
 * @Description:
 */
public interface DraggableListenerImp {

    void setOnItemDragListener(@Nullable OnItemDragListener onItemDragListener);

    void setOnItemSwipeListener(@Nullable OnItemSwipeListener onItemSwipeListener);
}
