package com.application.image.lib_ui.material_design.animation.valueanimation;

/**
 * Author: ml
 * Date: 2019/3/28 15:26
 * Description:
 */
public interface RecognizeObserver {
    //UI需在主线程中调用
    void updateRecogData(String state, String s1);
}
